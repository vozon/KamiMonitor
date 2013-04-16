package org.kami.monitor.api.core;

import org.kami.monitor.util.ParameterMap;

/**
 * Component Plug-in��ʼ��������
 * <p>
 * ���Ի�ȡComponent������������ԣ���Щ������Component����չ�߶��壬����HttpComponent,�������ԣ�
 * 1)trace-max-size
 * 2)trace-filter-active-time
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-4
 * @see org.kami.monitor.util.ParameterMap
 * @see org.kami.monitor.api.core.TraceLiftcycleManager
 */
public interface ComponentContext extends ParameterMap,Context{
	
	/**
	 * ��ȡ�켣����
	 * 
	 * @return
	 */
	public TraceLiftcycleManager getContainer();
	
	public Component getComponent();
	
	public void setComponent(Component component);
}
