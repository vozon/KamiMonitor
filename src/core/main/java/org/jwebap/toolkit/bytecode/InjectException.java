package org.jwebap.toolkit.bytecode;


/**
 * ��ע�����һ�㵱��ע���Ƿ��������׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  Mar 1, 2008
 */
public class InjectException extends Exception {

	private static final long serialVersionUID = 1L;


	public InjectException(String message) {
        super(message);
    }


    public InjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
