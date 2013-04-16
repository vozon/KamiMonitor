package org.kami.monitor.api.core;

import org.kami.monitor.api.cfg.model.ComponentDef;
import org.kami.monitor.api.cfg.model.JwebapDef;
import org.kami.monitor.api.cfg.persist.PersistManager;

/**
 * �����Ĺ���
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date Aug 7, 2007
 */
public class ContextFactory {

	/**
	 * ������������ģ����ĸ�������ΪRuntimeContext
	 * 
	 * @param container
	 * @param parent
	 * @param def
	 * @return
	 */
	public ComponentContext createComponentContext(
			TraceLiftcycleManager container, Context parent,
			ComponentDef def) {
		return new StandardComponentContext(container, parent, def);
	}

	/**
	 * ����RuntimeContext,һ��RuntimeContext����һ��jwebapʵ��
	 * 
	 * @param container
	 * @return
	 */
	public RuntimeContext createRuntimeContext(JwebapDef config,PersistManager defManager) {
		return new RuntimeContext(config,new TraceContainer(), defManager,this);
	}

}