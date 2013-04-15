package org.jwebap.cfg.persist.betwixt;

import org.jwebap.cfg.exception.BeanParseException;

/**
 * ��������Դ���󣬵�����Դ��ȡ��������ʱ�׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-19
 */
public class BeanInputException extends BeanParseException {

	static final long serialVersionUID = -19811222L;
	
	public BeanInputException(String message) {
		super(message);
	}

	public BeanInputException(String message, Throwable cause) {
		super(message, cause);
	}

}
