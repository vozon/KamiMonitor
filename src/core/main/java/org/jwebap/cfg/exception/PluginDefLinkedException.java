package org.jwebap.cfg.exception;

/**
 * ���ز�����ô��󣬵���������������ز������ʱ���������׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-19
 */
public class PluginDefLinkedException extends RuntimeException {
	
	static final long serialVersionUID = -1L;
	
	public PluginDefLinkedException(String message) {
		super(message);
	}

	public PluginDefLinkedException(String message, Throwable cause) {
		super(message, cause);
	}
}
