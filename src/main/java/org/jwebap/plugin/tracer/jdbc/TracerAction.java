package org.jwebap.plugin.tracer.jdbc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwebap.core.Analyser;
import org.jwebap.core.ComponentContext;
import org.jwebap.plugin.tracer.TimeFilterAnalyser;
import org.jwebap.ui.controler.ActionContext;
import org.jwebap.ui.controler.TemplateActionSupport;
import org.jwebap.ui.template.Context;
import org.jwebap.util.Assert;

/**
 * jdbc component����Action
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-1-11
 */
public class TracerAction extends TemplateActionSupport{

	public void process(HttpServletRequest request, HttpServletResponse response, Context context) throws Exception {

		/**���http����켣���������Ի�÷�������*/
		TimeFilterAnalyser analyser=getTimeFilterAnalyser();
		Assert.assertNotNull(analyser,"MethodComponent is not startup:time filter analyser is null.");
		
		
		//http����ʱ��ʱ��
		long time=analyser.getTracefilterActivetime();
		//http����ʱ�켣����¼��
		int max=analyser.getMaxTraceSize();
		
		/**����ģ��������*/
		context.put("overTime",new Long(time));
		context.put("maxTrace",new Integer(max));
	}


	/**
	 * ���ص�ǰAction��Ӧ��DBComponent��ִ��ʱ����˹켣������
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
