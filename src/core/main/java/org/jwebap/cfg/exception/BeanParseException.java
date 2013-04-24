package org.jwebap.cfg.exception;

/**
 * ����������󣬵���������ʱ��������ʱ�׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-19
 */
public class BeanParseException extends Exception {

	static final long serialVersionUID = -19811222L;
	
	public BeanParseException(String message) {
		super(message);
	}

	public BeanParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
