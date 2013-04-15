package org.jwebap.cfg.exception;


/**
 * jwebap����δ�ҵ��쳣,��jwebap����ʱδ�ҵ������ļ�ʱ�׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-11-23
 */
public class JwebapDefNotFoundException extends Exception {

	static final long serialVersionUID = -19811222L;
	
	public JwebapDefNotFoundException(String message) {
		super(message);
	}

	public JwebapDefNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
