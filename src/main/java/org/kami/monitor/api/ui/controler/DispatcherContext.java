package org.kami.monitor.api.ui.controler;

import org.kami.monitor.api.core.Context;
import org.kami.monitor.util.ParameterMap;

/**
 * 分发器上下文
 * <p>
 * 在Dispatcher初始化时使用的上下文
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2007-12-4
 * @see org.kami.monitor.api.ui.controler.Dispatcher
 */
public interface DispatcherContext extends ParameterMap,Context{
	
	public String getDispatcherPath();
	
}
