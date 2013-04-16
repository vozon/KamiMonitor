package org.kami.monitor.api.plugin.tracer.method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.kami.monitor.api.core.Analyser;
import org.kami.monitor.api.core.ComponentContext;
import org.kami.monitor.api.plugin.tracer.FrequencyAnalyser;
import org.kami.monitor.api.plugin.tracer.util.TraceStatViewHelper;
import org.kami.monitor.api.ui.controler.ActionContext;
import org.kami.monitor.api.ui.controler.JSONActionSupport;
import org.kami.monitor.util.Assert;

/**
 * method component time filter �����첽����ʱ�켣���ݵ���ӦAction
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-1-11
 */
public class StatDatasAction extends JSONActionSupport {

	public JSONObject processJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** ���http����켣���������Ի�÷������� */
		FrequencyAnalyser analyser = getFrequencyAnalyser();
		Assert.assertNotNull(analyser,
				"MethodComponent is not startup:stat analyser is null.");

		TraceStatViewHelper helper = new TraceStatViewHelper(analyser);

		return helper.processJson(request, response);
	}

	/**
	 * ���ص�ǰAction��Ӧ��HttpComponent��ִ��ʱ����˹켣������
	 * 
	 * @return ����ִ��ʱ����й��˵Ĺ켣������
	 * @see Analyser
	 */
	private FrequencyAnalyser getFrequencyAnalyser() {

		ActionContext actionContext = this.getActionContext();

		Assert.assertNotNull(actionContext, "actionContext is null.");

		ComponentContext componentContext = actionContext.getComponentContext();

		Assert.assertNotNull(componentContext, "componentContext is null.");

		MethodComponent component = (MethodComponent) componentContext
				.getComponent();

		/** ���httpͳ�Ʒ��������Ի�÷������� */
		FrequencyAnalyser analyser = component.getFrequencyAnalyser();

		return analyser;
	}
}
