package org.jwebap.cfg.persist;

import org.jwebap.cfg.exception.BeanParseException;

/**
 * ��ȡBean�ӿ�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-4-13
 * 
 */
public interface BeanReader {
	
	public Object parse() throws BeanParseException;
}
