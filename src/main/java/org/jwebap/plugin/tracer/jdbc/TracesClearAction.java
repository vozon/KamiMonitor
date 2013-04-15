package org.jwebap.plugin.tracer.jdbc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwebap.core.Analyser;
import org.jwebap.core.ComponentContext;
import org.jwebap.plugin.tracer.TimeFilterAnalyser;
import org.jwebap.ui.controler.Action;
import org.jwebap.ui.controler.ActionContext;
import org.jwebap.util.Assert;

/**
 * JdbcComponent ��������չ켣 Action
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-2-4
 */
public class TracesClearAction extends Action {

	/**
	 * ��չ켣����
	 */
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Analyser analyser=getTimeFilterAnalyser();
		analyser.clear();
		
	}
	
	/**
	 * ���ص�ǰAction��Ӧ��HttpComponent��ִ��ʱ����˹켣������
	 * 
	 * @return ����ִ��ʱ����й��˵Ĺ켣������
	 * @see Analyser
	 */
	private TimeFilterAnalyser getTimeFilterAnalyser() {

		ActionContext actionContext = this.getActionContext();

		Assert.assertNotNull(actionContext, "actionContext is null.");

		ComponentContext componentContext = actionContext.getComponentContext();

		Assert.assertNotNull(componentContext, "componentContext is null.");

		JdbcComponent component = (JdbcComponent) componentContext
				.getComponent();

		/** ���http����켣���������Ի�÷������� */
		TimeFilterAnalyser analyser = component.getTimeAnalyser();

		return analyser;
	}

}
