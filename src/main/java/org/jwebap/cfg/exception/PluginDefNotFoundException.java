package org.jwebap.cfg.exception;


/**
 * �������δ�ҵ�����
 * 
 * ��������ö�Ӧ�Ĳ����ļ�δ�ҵ�ʱ�׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-1
 */
public class PluginDefNotFoundException extends Exception {
	
	static final long serialVersionUID = -1L;
	
	public PluginDefNotFoundException(String message) {
		super(message);
	}

	public PluginDefNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
