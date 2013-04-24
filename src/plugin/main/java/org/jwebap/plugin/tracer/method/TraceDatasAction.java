package org.jwebap.plugin.tracer.method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.jwebap.core.Analyser;
import org.jwebap.core.ComponentContext;
import org.jwebap.plugin.tracer.TimeFilterAnalyser;
import org.jwebap.plugin.tracer.util.TimeFilterViewHelper;
import org.jwebap.ui.controler.ActionContext;
import org.jwebap.ui.controler.JSONActionSupport;
import org.jwebap.util.Assert;

/**
 * method component tracer �����첽����ʱ�켣���ݵ���ӦAction
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-1-11
 */
public class TraceDatasAction extends JSONActionSupport {

	public JSONObject processJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** ���http����켣���������Ի�÷������� */
		TimeFilterAnalyser analyser = getTimeFilterAnalyser();
		Assert.assertNotNull(analyser,
				"MethodComponent is not startup:time filter analyser is null.");

		TimeFilterViewHelper helper = new TimeFilterViewHelper(analyser);

		return helper.processJson(request, response);
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

		MethodComponent component = (MethodComponent) componentContext
				.getComponent();

		/** ���http����켣���������Ի�÷������� */
		TimeFilterAnalyser analyser = component.getTimeAnalyser();

		return analyser;
	}
}