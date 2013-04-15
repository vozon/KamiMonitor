package org.jwebap.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwebap.cfg.model.JwebapDef;
import org.jwebap.cfg.persist.PersistManager;
import org.jwebap.core.RuntimeContext;
import org.jwebap.startup.Startup;
import org.jwebap.ui.controler.Action;

/**
 * pluginɾ��action
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-11-28
 */
public class PluginRemoveAction  extends Action {

	/**
	 * ɾ��plugin���ұ���jwebap.xml
	 */
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pluginName=request.getParameter("pluginName");
		RuntimeContext context=Startup.getRuntimeContext();
		PersistManager jwebapDefManager=context.getJwebapDefManager();
		//���»�ȡһ���µ�jwebap���ö��壬����ȥʹ��jwebap����ʵ���е����ö��󣬱��ֶ����õ��޸Ĳ�Ӱ�����л���
		JwebapDef def=jwebapDefManager.get();
		def.removePluginDef(pluginName);
		
		jwebapDefManager.save(def);
		
	}

}
