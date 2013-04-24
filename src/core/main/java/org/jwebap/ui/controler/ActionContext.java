package org.jwebap.ui.controler;

import org.jwebap.cfg.model.ActionDef;
import org.jwebap.core.Component;
import org.jwebap.core.ComponentContext;
import org.jwebap.core.Context;

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
