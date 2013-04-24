package com.coder.monitor.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.instrumentation.SourceCounter;
import org.apache.flume.source.AbstractSource;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.security.SecurityHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Receives HTTP request from remote client and transfers parameter to unit of Flume
 * 
 * @author Truman
 * @author xiang min
 * @since 2013-4-5
 * @version 1.0
 */
public class HttpSource extends AbstractSource implements EventDrivenSource, Configurable {

	private static final Logger LOG = LoggerFactory.getLogger(HttpSource.class);
	private static final String JETTY_CONFIG_XML = "jetty.xml";
	private static final String COMMA = ",";
	private static final String EMPTY_VALUE = "";
	private static final String ALLOWED_HTTP_METHOD = "GET";
	private static final String MIME_TYPE="image/gif";
	private Schema schema4Pb;
	private Server jettyServer;
	private SourceCounter sourceCounter;
	
	@Override
	public void configure(Context context) {
		
		//FIXME as a temporary solution,load shcema hard code
		try {
			Schema.Parser parser = new Schema.Parser();
			schema4Pb = parser.parse(HttpSource.class.getResourceAsStream("/message4PB.avsc"));
		} catch (Exception e) {
			String msg = "Failed to initialize the HttpServer";
			LOG.error("HttpEventSource:" + msg, e);
			throw new RuntimeException(msg + ", cause:" + e.getMessage());
		}
	}

	@Override
	public void start() {
		LOG.info("HTTP source starting");
		try {
			jettyServer = new Server();
			XmlConfiguration configuration = new XmlConfiguration(HttpSource.class.getClassLoader().getResourceAsStream(JETTY_CONFIG_XML));
			configuration.configure(jettyServer);
			org.mortbay.jetty.servlet.Context root = new org.mortbay.jetty.servlet.Context(jettyServer, "/", org.mortbay.jetty.servlet.Context.SESSIONS);
			//security-constraint
			SecurityHandler securityHandler = new SecurityHandler();
			securityHandler.setAuthMethod(ALLOWED_HTTP_METHOD);
			root.setSecurityHandler(securityHandler);
			root.addServlet(new ServletHolder(new FlumeHTTPServlet()), "/pic.gif");
			jettyServer.start();
		} catch (SAXException e) {
			LOG.error("parse jetty.xml error", e);
		} catch (IOException e) {
			LOG.error("load jetty.xml error", e);
		} catch (Exception e) {
			LOG.error("jetty Server start error", e);
		}
		// // FIXME: should return or throw exception
		
		if (sourceCounter == null) {
			sourceCounter = new SourceCounter(getName());
		}
		sourceCounter.start();
		super.start();
		LOG.info("HTTP source started");
	}

	@Override
	public void stop() {

		LOG.info("HTTP source stopping");
		if (jettyServer != null) {
			try {
				jettyServer.stop();
				jettyServer.join();
			} catch (Exception e) {
				LOG.error("Could not close Jetty server");
			}
		}
		sourceCounter.stop();
		super.stop();

		LOG.info("HTTP source stopped");
	}

	private class FlumeHTTPServlet extends HttpServlet {

		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String rawdataId = request.getParameter("rawDataID");
			String message = request.getParameter("msg");
			if(StringUtils.isNotEmpty(message) && StringUtils.isNotEmpty(rawdataId)){
				String value = convertPara2Csv(message);
				// FIXME:if value is empty?
				//
				Event event = new SimpleEvent();
				Map<String, String> header = new HashMap<String, String>();
				// add rawDataId to event header.
				header.put(HeaderName.HEADER_CATEGORY, rawdataId);
				event.setHeaders(header);
				event.setBody(value.getBytes());
				LOG.info("Receive event successfully.");
				getChannelProcessor().processEvent(event);
				sourceCounter.incrementEventAcceptedCount();

				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				response.setContentType(MIME_TYPE);
				return;
			}
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.flushBuffer();
		}
	}
	
	/**
	 * convert a JSON string to a csv String
	 * @param message
	 * @return csv 
	 */
	private String convertPara2Csv(String message){
		if (StringUtils.isNotEmpty(message)) {
			List<Field> fields = schema4Pb.getFields();
			JSONObject messageJO = null;
			try {
				messageJO = JSONObject.fromObject(message);
			} catch (Exception e) {
				LOG.error("parse message error", e);
			}
			StringBuffer values = new StringBuffer();
			String fieldValue, value;
			if (null != fields && fields.size() > 0 && null != messageJO) {
				for (Field field : fields) {
					try {
						fieldValue = messageJO.getString(field.name());
					} catch (Exception e) {
						fieldValue = EMPTY_VALUE;
					}
					values.append(fieldValue).append(COMMA);
				}
			}
			if (values.length() > 0) {
				//add timestamp
				//value = System.currentTimeMillis() + values.substring(0, values.length() - 1);
				values.insert(0, System.currentTimeMillis());
				return values.toString();
			}
		}
		return StringUtils.EMPTY;
	}
}
