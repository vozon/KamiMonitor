package org.jwebap.cfg.exception;

/**
 * ����־û����󣬵�����־û�ʱ��������ʱ�׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-19
 */
public class BeanWriteException extends Exception {

	static final long serialVersionUID = -19811222L;
	
	public BeanWriteException(String message) {
		super(message);
	}

	public BeanWriteException(String message, Throwable cause) {
		super(message, cause);
	}

}
