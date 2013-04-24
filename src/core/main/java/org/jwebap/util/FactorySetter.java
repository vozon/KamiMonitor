package org.jwebap.util;

import org.jwebap.cfg.model.JwebapDef;
import org.jwebap.core.RuntimeContext;
import org.jwebap.startup.Startup;
import org.jwebap.ui.controler.ActionFactory;
import org.jwebap.ui.controler.DispatcherFactory;

/**
 * ���󹤳��������ڴ�������ֱ�������ڸ�factory���Ժ󽫸Ľ�Ϊ����iocע��ģʽ
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-27
 * @deprecated
 */
public class FactorySetter {

	private static ActionFactory actionFactory = null;

	private static DispatcherFactory dispatcherFactory = null;

	public static ActionFactory getActionFactory() {
		if(actionFactory!=null){
			return actionFactory;
		}
		
		RuntimeContext context =Startup.getRuntimeContext();
		JwebapDef def=context.getJwebapDef();
		actionFactory = new ActionFactory(def);
		
		return actionFactory;
	}

	public static void setActionFactory(ActionFactory actionFactory) {
		FactorySetter.actionFactory = actionFactory;
	}

	public static DispatcherFactory getDispatcherFactory() {
		if(dispatcherFactory!=null){
			return dispatcherFactory;
		}
		
		RuntimeContext context =Startup.getRuntimeContext();
		JwebapDef def=context.getJwebapDef();
		dispatcherFactory = new DispatcherFactory(def);
		
		return dispatcherFactory;
	}

	public static void setDispatcherFactory(DispatcherFactory dispatcherFactory) {
		FactorySetter.dispatcherFactory = dispatcherFactory;
	}
}
