package org.jwebap.startup;

/**
 * �����ʼ���������еĲ�����ش����������࣬������PluginDefNotFoundException,ConponentInitialException�ȵȣ�
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-4
 * @see PluginDefNotFoundException,ConponentInitialException
 */
public class PluginInitialException extends RuntimeException {
	
	static final long serialVersionUID = -1L;
	
	public PluginInitialException(String message) {
		super(message);
	}

	public PluginInitialException(String message, Throwable cause) {
		super(message, cause);
	}
}
