package com.coder.monitor.demo;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedTomcat2 {
	private static transient Logger logger = LoggerFactory
			.getLogger(EmbeddedTomcat.class);

	/**
	 * init the web.xml into the JVM.
	 * 
	 * @throws Exception
	 * */
	public static void start(int port) throws Exception {
		String FEN_GE = File.separator;
		try {
			long begin = System.currentTimeMillis();
			// String addr = System.getenv("DACONTROLLER_HOME");
			// if (null == addr) {
			// throw new RuntimeException(
			// "the ENV variable ${DACONTROLLER_HOME} is null ");
			// }
			// addr = addr + "/webapp";
			File webapp = new File(EmbeddedTomcat.class.getClassLoader()
					.getResource("").toURI());
			String addr = webapp.getAbsolutePath();

			addr = addr.substring(0, addr.indexOf("KamiMonitor")
					+ "KamiMonitor".length())
					+ FEN_GE + "src" + FEN_GE + "main" + FEN_GE + "webapp";
			logger.info(" Tomcat Home " + addr);
			System.setProperty("catalina.base", addr);

			Tomcat tomcat = new Tomcat();
			tomcat.setPort(port);
			tomcat.getHost().setAppBase(addr);
			tomcat.
			//tomcat.addWebapp("/", addr);

			tomcat.start();
			logger.info("Tomcat Server started, use "
					+ (System.currentTimeMillis() - begin) + " ms");
			tomcat.getServer().await();
		} catch (Throwable e) {
			logger.error("Start Tomcat Error ", e);
			throw new RuntimeException("Start Tomcat Error", e);
		}
	}

	public static void main(String[] args) throws Exception {
		EmbeddedTomcat.start(8090);
	}
}