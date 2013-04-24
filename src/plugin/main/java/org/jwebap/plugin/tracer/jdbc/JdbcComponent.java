package org.jwebap.plugin.tracer.jdbc;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.core.Component;
import org.jwebap.core.ComponentContext;
import org.jwebap.core.TraceLiftcycleManager;
import org.jwebap.plugin.tracer.TimeFilterAnalyser;
import org.jwebap.toolkit.bytecode.ClassEnhancer;

/**
 * Jdbc������
 * 
 * ���ڱ������ӳأ����Բ�ȡ���������ķ�ʽ����̬��أ�����Ӧ�ò���oracle,mysql
 * �������ݿ⣬�����ֱ���orcale.jdbc.driver.OracleDriver��
 * com.mysql.jdbc.Driver����ô��DBComponent������������
 * driver-clazzs=orcale.jdbc.driver.OracleDriver;com.mysql.jdbc.Driver
 * 
 * ����jndiԶ������Դ����������Ӧ�������ڻ�ȡ���ӵ�����Ϊ����������
 * com.ConnectionManager��DBComponent��������ʱ����Щ���������ע�룬ʹ������
 * ���෽�����ص�Connection���м�صĹ��ܡ�
 * 
 * @see JdbcDriverInjectHandle
 * @author leadyu
 * @since Jwebap 0.5
 * @date 2007-8-14
 */
public class JdbcComponent implements Component {
	private static final Log log = LogFactory.getLog(JdbcComponent.class);

	public static final Object DB_TRACE_TYPE = new Object();

	private TimeFilterAnalyser timeAnalyser = null;

	private ComponentContext componentContext = null;

	List _connectionOpenListeners;

	public JdbcComponent() {
		_connectionOpenListeners = new Vector();
	}

	public void startup(ComponentContext context) {
		componentContext = context;
		TraceLiftcycleManager container = componentContext.getContainer();

		timeAnalyser = new TimeFilterAnalyser();
		String maxSize = context.getProperty("trace-max-size");
		String activeTime = context.getProperty("trace-filter-active-time");

		try{
			timeAnalyser.setMaxTraceSize(Integer.parseInt(maxSize));
		}catch(Exception e){timeAnalyser.setMaxTraceSize(1000);}
		try{
			timeAnalyser.setTracefilterActivetime(Integer.parseInt(activeTime));
		}catch(Exception e){timeAnalyser.setTracefilterActivetime(-1);}
	
		container.registerAnalyser(DB_TRACE_TYPE, timeAnalyser);

		String clazzs = context.getProperty("driver-clazzs");
		String[] drivers = getDriverClassNames(clazzs);

		// ���ؼ�����
		String listenerClasses = context.getProperty("connection-listener");
		listenerClasses = listenerClasses.replaceAll("\n", "");
		listenerClasses = listenerClasses.replaceAll("\r", "");
		listenerClasses = listenerClasses.replaceAll("\t", "");
		listenerClasses = listenerClasses.trim();

		String[] lClasses = listenerClasses.split(";");

		for (int i = 0; i < lClasses.length; i++) {
			try {
				if (lClasses[i].trim().length() > 0) {
					Class listenerInstance = Class.forName(lClasses[i]);
					ConnectionEventListener listener = (ConnectionEventListener) listenerInstance
							.newInstance();
					addConnectionOpenListener(listener);
				}
			} catch (Exception e) {
				log.warn("create connection listener " + lClasses[i]
						+ " error. ");
			}
		}
		
		// inject trace
		injectJdbcDriver(drivers);
		
		log.info("jdbccomponent startup.");
	}

	public void addConnectionOpenListener(ConnectionEventListener listener) {
		_connectionOpenListeners.add(listener);
	}

	public void clearConnectionOpenListener() {
		_connectionOpenListeners.clear();
	}

	public List getConnectionOpenListener() {
		return _connectionOpenListeners;
	}

	private String[] getDriverClassNames(String clazzs) {
		if (clazzs == null || "".equals(clazzs)) {
			return new String[0];
		}
		clazzs = clazzs.replaceAll("\n", "");
		clazzs = clazzs.replaceAll("\r", "");
		clazzs = clazzs.replaceAll("\t", "");
		clazzs = clazzs.replaceAll(" ", "");
		try {
			return clazzs.split(";");
		} catch (Exception e) {
			return new String[0];
		}
	}

	private void injectJdbcDriver(String[] drivers) {
		ClassEnhancer enhancer = new ClassEnhancer();
		for (int i = 0; i < drivers.length; i++) {

			String className = drivers[i];
			try {
				TraceLiftcycleManager container = componentContext
						.getContainer();

				int size = _connectionOpenListeners.size();
				ConnectionEventListener[] listeners = new ConnectionEventListener[size];
				System.arraycopy(_connectionOpenListeners.toArray(), 0,
						listeners, 0, size);

				// inject jdbcdriver to delegate connection from local pool or
				// remote pool.
				enhancer.createClass(className, new JdbcDriverInjectHandle(
						container, listeners), true);

			} catch (Throwable e) {
				e.printStackTrace();
				log.warn("�����ݿ�������" + className + "�ļ�������ʧ�ܣ������Ⲣ��Ӱ��ϵͳ�����С�");

			}

		}
	}

	public void destory() {
		timeAnalyser.clear();
		TraceLiftcycleManager container = componentContext.getContainer();
		container.unregisterAnalyser(DB_TRACE_TYPE, timeAnalyser);
		timeAnalyser = null;
	}

	public void clear() {
		timeAnalyser.clear();
	}

	public TimeFilterAnalyser getTimeAnalyser() {
		return timeAnalyser;
	}

	public void setTimeAnalyser(TimeFilterAnalyser timeAnalyser) {
		TraceLiftcycleManager container = componentContext.getContainer();
		container.unregisterAnalyser(DB_TRACE_TYPE, this.timeAnalyser);
		this.timeAnalyser.clear();
		container.registerAnalyser(DB_TRACE_TYPE, timeAnalyser);
		this.timeAnalyser = timeAnalyser;
	}

	public ComponentContext getComponentContext() {
		return componentContext;
	}

}
