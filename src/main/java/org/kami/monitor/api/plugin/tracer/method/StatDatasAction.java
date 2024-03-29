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
 * method component time filter 界面异步请求超时轨迹数据的响应Action
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-1-11
 */
public class StatDatasAction extends JSONActionSupport {

	public JSONObject processJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** 获得http请求轨迹分析器，以获得分析数据 */
		FrequencyAnalyser analyser = getFrequencyAnalyser();
		Assert.assertNotNull(analyser,
				"MethodComponent is not startup:stat analyser is null.");

		TraceStatViewHelper helper = new TraceStatViewHelper(analyser);

		return helper.processJson(request, response);
	}

	/**
	 * 返回当前Action对应的HttpComponent的执行时间过滤轨迹分析器
	 * 
	 * @return 根据执行时间进行过滤的轨迹分析器
	 * @see Analyser
	 */
	private FrequencyAnalyser getFrequencyAnalyser() {

		ActionContext actionContext = this.getActionContext();

		Assert.assertNotNull(actionContext, "actionContext is null.");

		ComponentContext componentContext = actionContext.getComponentContext();

		Assert.assertNotNull(componentContext, "componentContext is null.");

		MethodComponent component = (MethodComponent) componentContext
				.getComponent();

		/** 获得http统计分析器，以获得分析数据 */
		FrequencyAnalyser analyser = component.getFrequencyAnalyser();

		return analyser;
	}
}
