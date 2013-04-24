package org.jwebap.util;

/**
 * �������϶���ͨ�����ַ�����ɵ�name��value��ֵ�����
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-4
 */
public interface ParameterMap{

	public String getProperty(String key);

	public String[] propNames();
	
	public void putProperty(String key, String value);

}
