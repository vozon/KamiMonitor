package org.kami.monitor.util;

import org.kami.monitor.api.Startup;
import org.kami.monitor.api.cfg.model.JwebapDef;
import org.kami.monitor.api.core.RuntimeContext;
import org.kami.monitor.api.ui.controler.ActionFactory;
import org.kami.monitor.api.ui.controler.DispatcherFactory;

/**
 * 抽象工厂，这里在代码里面直接依赖于各factory，以后将改进为类似ioc注入模式
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
