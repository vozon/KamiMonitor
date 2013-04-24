package com.coder.monitor.demo;

import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcat {
	private static Tomcat tomcat = null;
	private static String CONTEXT_PATH = "\\KamiMonitor";
	private static String PROJECT_PATH = System.getProperty("user.dir");
	private static String WEB_APP_PATH = PROJECT_PATH + "\\src\\main\\webapps\\sample";
	//private static String TOMCAT_HOME = PROJECT_PATH + "\\Embedded/Tomcat";
	private static String ENCODING = "UTF-8";
	private static int TOMCAT_PORT = 8090;

	// ����Ƕ��ʽTomcat������
	public static void startTomcat() throws Exception {
		try {
			long startTime = System.currentTimeMillis();
			ContextConfig cc = new ContextConfig();
			tomcat = new Tomcat();
            // ����Tomcat�Ĺ���Ŀ¼:���̸�Ŀ¼/Embedded/Tomcat
            tomcat.setBaseDir(TOMCAT_HOME);
            tomcat.setPort(TOMCAT_PORT);
            tomcat.addWebapp("/sample/", WEB_APP_PATH);
            tomcat.enableNaming();//ִ��������֧��JDNI����
            tomcat.getConnector().setURIEncoding(ENCODING);
			tomcat.start();
			System.err.println("Tomcat started in " + (System.currentTimeMillis() - startTime) + " ms.");
			tomcat.getServer().await();//�÷�����һֱ��
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			EmbeddedTomcat.startTomcat();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
