package org.kami.monitor.api.ui.controler;

import org.kami.monitor.api.cfg.model.ActionDef;
import org.kami.monitor.api.core.Component;
import org.kami.monitor.api.core.ComponentContext;
import org.kami.monitor.api.core.Context;

/**
 * Action�����ģ����Ի�ȡ��ǰAction��Ӧ��Component�����
 * ���������ͼ����������Ĺ�����͸���ģ���ͼ����Ҫ�Ļ������ݶ���ͨ��ActionContext��á�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2007-12-14
 */
public interface ActionContext extends Context{

	
	/**
	 * �õ���ǰAction��Ӧ��Component������
	 * @return
	 */
	public Component getComponent();
	
	/**
	 * �õ�Action��Ӧ�������������
	 * @return
	 */
	public ComponentContext getComponentContext();

	public ActionDef getActionDefinition();
}
