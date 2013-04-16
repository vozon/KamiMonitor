package org.kami.monitor.api.ui.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.kami.monitor.api.Startup;
import org.kami.monitor.api.cfg.model.JwebapDef;
import org.kami.monitor.api.cfg.model.PluginDefRef;
import org.kami.monitor.api.cfg.persist.PersistManager;
import org.kami.monitor.api.core.RuntimeContext;
import org.kami.monitor.api.ui.controler.JSONActionSupport;

/**
 * 返回当前plugin的components
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-11-25
 */
public class ComponentListAction  extends JSONActionSupport {

	/**
	 * 返回当前plugin的components
	 */
	public JSONObject processJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RuntimeContext context=Startup.getRuntimeContext();
		PersistManager jwebapDefManager=context.getJwebapDefManager();
		JwebapDef def=jwebapDefManager.get();
		PluginDefRef plugin=null;
		String pluginName=request.getParameter("pluginName");
		if(pluginName==null){
			pluginName=(String)request.getAttribute("pluginName");
		}
		plugin=def.getPluginDef(pluginName);
		
		JSONObject json = new JSONObject();
		if(plugin!=null){
			json.put("components", plugin.getComponentDefs());
		}else{
			json.put("components", new ArrayList());
		}
		return json;
		
	}
}
