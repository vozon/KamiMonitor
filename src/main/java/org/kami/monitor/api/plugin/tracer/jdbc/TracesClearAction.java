package org.kami.monitor.api.plugin.tracer.jdbc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kami.monitor.api.core.Analyser;
import org.kami.monitor.api.core.ComponentContext;
import org.kami.monitor.api.plugin.tracer.TimeFilterAnalyser;
import org.kami.monitor.api.ui.controler.Action;
import org.kami.monitor.api.ui.controler.ActionContext;
import org.kami.monitor.util.Assert;

/**
 * JdbcComponent 分析器清空轨迹 Action
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-2-4
 */
public class TracesClearAction extends Action {

	/**
	 * 清空轨迹处理
	 */
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Analyser analyser=getTimeFilterAnalyser();
		analyser.clear();
		
	}
	
	/**
	 * 返回当前Action对应的HttpComponent的执行时间过滤轨迹分析器
	 * 
	 * @return 根据执行时间进行过滤的轨迹分析器
	 * @see Analyser
	 */
	private TimeFilterAnalyser getTimeFilterAnalyser() {

		ActionContext actionContext = this.getActionContext();

		Assert.assertNotNull(actionContext, "actionContext is null.");

		ComponentContext componentContext = actionContext.getComponentContext();

		Assert.assertNotNull(componentContext, "componentContext is null.");

		JdbcComponent component = (JdbcComponent) componentContext
				.getComponent();

		/** 获得http请求轨迹分析器，以获得分析数据 */
		TimeFilterAnalyser analyser = component.getTimeAnalyser();

		return analyser;
	}

}
