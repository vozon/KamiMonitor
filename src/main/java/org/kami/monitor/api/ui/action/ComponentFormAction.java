package org.kami.monitor.api.ui.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kami.monitor.api.Startup;
import org.kami.monitor.api.cfg.model.ComponentDef;
import org.kami.monitor.api.cfg.model.JwebapDef;
import org.kami.monitor.api.cfg.persist.PersistManager;
import org.kami.monitor.api.core.RuntimeContext;
import org.kami.monitor.api.ui.controler.TemplateActionSupportHelper;
import org.kami.monitor.api.ui.template.Context;

/**
 * component表单界面action
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-11-28
 */
public class ComponentFormAction  extends TemplateActionSupportHelper {

	/**
	 * 展现component参数
	 */
	public void process(Context context) throws Exception {
		String componentName=(String)context.get("componentName");
		
		RuntimeContext runtimeContext=Startup.getRuntimeContext();
		PersistManager jwebapDefManager=runtimeContext.getJwebapDefManager();
		JwebapDef def=jwebapDefManager.get();
		ComponentDef componentDef=null;
		if(componentName!=null){
			componentDef=def.getComponentDef(componentName);
		}
		
		if(componentDef!=null){
			context.put("name", componentDef.getName());
			context.put("class", componentDef.getType());
			Collection params=componentDef.getProperties();
			context.put("params",params);
		}
		
		
	}
}
