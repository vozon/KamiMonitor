package org.kami.monitor.api.ui.template.commontemplate;

import org.commontemplate.core.Template;
import org.kami.monitor.api.ui.template.Context;
import org.kami.monitor.api.ui.template.MergeException;
import org.kami.monitor.util.Assert;

/**
 * Common Template Template Adapter
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2007-11-26
 */
public class CTLTemplate implements org.kami.monitor.api.ui.template.Template {
	
	private Template _template=null;
	
	public CTLTemplate(Template template){
		_template=template;
	}
	
	public void merge(Context context) throws MergeException {
		Assert.assertNotNull(_template,"CommonTemplate模版为空!");
		
		try{
			CTLContext ctlContext=(CTLContext)context;
			org.commontemplate.core.Context delegate=(org.commontemplate.core.Context)ctlContext.getDelegate();
			_template.render(delegate);
		}catch(ClassCastException e){
			throw new ClassCastException("不能绑定CommonTemplate以外的上下文对象!");
		}
	}

}
