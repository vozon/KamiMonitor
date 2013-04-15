package org.jwebap.ui.controler;

import org.jwebap.cfg.model.ActionDef;
import org.jwebap.core.AbstractContext;
import org.jwebap.core.Component;
import org.jwebap.core.ComponentContext;
import org.jwebap.core.Context;

/**
 * ģ�洦��Action�����ģ����ĸ�������ΪComponentContext
 * ���Ի�ȡ��ǰAction��Ӧ��Component��������������ͼ����������Ĺ�����͸���ģ� ��ͼ����Ҫ�Ļ������ݶ���ͨ��ActionContext��á�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-14
 */
public class TemplateActionContext extends AbstractContext implements
		ActionContext {

	ActionDef _config = null;

	public TemplateActionContext(ActionDef config) {
		_config = config;
	}
	
	public TemplateActionContext(Context context, ActionDef config) {
		super(context);
		_config = config;
	}

	/**
	 * �õ���ǰAction��Ӧ��Component������
	 * 
	 * @return
	 */
	public Component getComponent() {
		ComponentContext context = (ComponentContext) getParent();
		return context.getComponent();
	}

	public ComponentContext getComponentContext() {
		ComponentContext context = (ComponentContext) getParent();
		return context;
	}

	public ActionDef getActionDefinition() {
		return _config;
	}
	
	public String getTemplateName() {
		return _config.getTemplate();
	}
	
}
