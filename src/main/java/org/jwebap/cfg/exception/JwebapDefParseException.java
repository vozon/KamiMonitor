package org.jwebap.cfg.exception;

/**
 * jwebap���ý������󣬵�����jwebap.xml��������ʱ�׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-19
 */
public class JwebapDefParseException extends Exception {

	static final long serialVersionUID = -19811222L;
	
	public JwebapDefParseException(String message) {
		super(message);
	}

	public JwebapDefParseException(String message, Throwable cause) {
		super(message, cause);
	}

}
