package org.kami.monitor.api.ui.controler;

import org.kami.monitor.api.cfg.model.ActionDef;
import org.kami.monitor.api.core.AbstractContext;
import org.kami.monitor.api.core.Component;
import org.kami.monitor.api.core.ComponentContext;
import org.kami.monitor.api.core.Context;

/**
 * 模版处理Action上下文，它的父上下文为ComponentContext
 * 可以获取当前Action对应的Component组件。对于组件视图，组件容器的管理是透明的， 视图所需要的环境数据都是通过ActionContext获得。
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
	 * 得到当前Action对应的Component上下文
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
