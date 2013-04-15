package org.jwebap.startup;

/**
 * ����JwebapInitialException������ָ��Jwebap�������̷������쳣
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-4
 */
public class JwebapInitialException  extends RuntimeException {

	static final long serialVersionUID = -19811222L;
	
	public JwebapInitialException(String message) {
		super(message);
	}

	public JwebapInitialException(String message, Throwable cause) {
		super(message, cause);
	}

}
