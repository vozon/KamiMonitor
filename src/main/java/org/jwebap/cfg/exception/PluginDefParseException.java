package org.jwebap.cfg.exception;

/**
 * ���������ش��󣬵������������ʱ���������׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-19
 */
public class PluginDefParseException extends Exception {
	
	static final long serialVersionUID = -1L;
	
	public PluginDefParseException(String message) {
		super(message);
	}

	public PluginDefParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
