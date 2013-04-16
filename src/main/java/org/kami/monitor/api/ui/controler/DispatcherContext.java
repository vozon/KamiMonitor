package org.kami.monitor.api.ui.controler;

import org.kami.monitor.api.core.Context;
import org.kami.monitor.util.ParameterMap;

/**
 * �ַ���������
 * <p>
 * ��Dispatcher��ʼ��ʱʹ�õ�������
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2007-12-4
 * @see org.kami.monitor.api.ui.controler.Dispatcher
 */
public interface DispatcherContext extends ParameterMap,Context{
	
	public String getDispatcherPath();
	
}
