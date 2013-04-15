package org.jwebap.ui.controler;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwebap.cfg.model.ActionDef;
import org.jwebap.ui.template.Context;
import org.jwebap.ui.template.EngineFactory;
import org.jwebap.ui.template.Template;
import org.jwebap.ui.template.TemplateEngine;

/**
 * ����ģ�����������
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-14
 * @deprecated ����ģ��Action�Լ���ģ��Action�Ĵ���ͳһ������Ҫ������ģ��Action���⴦��,ת�ɸ���ActionSupport��֧��
 * @see org.jwebap.ui.controler.TemplateActionSupport
 */
public class TemplateRequestProcesser extends ActionProcesser {
	AbstractDispatcher dispatcher;
	public TemplateRequestProcesser(AbstractDispatcher dispatcher, ActionFactory factory) {
		super(factory);
		this.dispatcher=dispatcher;
	}

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String path = dispatcher.getSubPath(request);
		Action action = createAction(path);

		TemplateEngine engine = EngineFactory
				.getEngine(EngineFactory.COMMON_TEMPLATE);

		Writer out = response.getWriter();

		Context templateContext = null;

		ActionDef config = action.getActionContext()
				.getActionDefinition();
		String templateName = config.getTemplate();
		Template template = null;
		if (templateName != null && !"".equals(templateName)) {
			template = engine.getTemplate(templateName);
			templateContext = engine.createContext(out);
		}
		/**
		 * ����ģ�������ģ�����ģ���Action����ʱ��ȡ��������
		 * 
		 * @see TemplateActionSupport
		 */
		//request.setAttribute(TemplateActionSupport.TEMPLATE_CONTEXT_ATTRIBUTE,templateContext);

		action.process(request, response);

		if (template != null) {
			template.merge(templateContext);
		}
	}

}
