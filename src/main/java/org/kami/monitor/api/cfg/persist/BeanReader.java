package org.kami.monitor.api.cfg.persist;

import org.kami.monitor.api.cfg.exception.BeanParseException;

/**
 * 获取Bean接口
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-4-13
 * 
 */
public interface BeanReader {
	
	public Object parse() throws BeanParseException;
}
