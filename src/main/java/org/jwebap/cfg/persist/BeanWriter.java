package org.jwebap.cfg.persist;

import org.jwebap.cfg.exception.BeanWriteException;

/**
 * ��beanд�뵽��
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-4-13
 */
public interface BeanWriter {
	/**
	 * ��Beanд����
	 * 
	 * @param bean
	 */
	public void write(Object bean) throws BeanWriteException;
	
}
