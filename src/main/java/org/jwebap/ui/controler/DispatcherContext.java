package org.jwebap.ui.controler;

import org.jwebap.core.Context;
import org.jwebap.util.ParameterMap;

/**
 * �ַ���������
 * <p>
 * ��Dispatcher��ʼ��ʱʹ�õ�������
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2007-12-4
 * @see org.jwebap.ui.controler.Dispatcher
 */
public interface DispatcherContext extends ParameterMap,Context{
	
	public String getDispatcherPath();
	
}
