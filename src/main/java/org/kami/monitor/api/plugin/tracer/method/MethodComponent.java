package org.kami.monitor.api.plugin.tracer.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kami.monitor.api.core.Component;
import org.kami.monitor.api.core.ComponentContext;
import org.kami.monitor.api.core.TraceLiftcycleManager;
import org.kami.monitor.api.plugin.tracer.FrequencyAnalyser;
import org.kami.monitor.api.plugin.tracer.TimeFilterAnalyser;
import org.kami.monitor.api.toolkit.bytecode.ClassEnhancer;
import org.kami.monitor.api.toolkit.bytecode.PackageEnhancer;

/**
 * @className MethodComponent:类监控组件
 * 
 * 可以对类，对包进行监控
 * 
 * @link TraceMethodHandle
 * @author leadyu
 * @since Jwebap 0.5
 * @date  2007-8-14
 */
public class MethodComponent implements Component{
	private static final Log log = LogFactory.getLog(MethodComponent.class);
	private ComponentContext componentContext=null;
	
	public static final Object METHOD_TRACE_TYPE=new Object();
	
	protected TimeFilterAnalyser timeAnalyser=null;
	protected FrequencyAnalyser  frequencyAnalyser=null;
	
	
	public void startup(ComponentContext context) {
		componentContext=context;
		TraceLiftcycleManager container=componentContext.getContainer();

		timeAnalyser=new TimeFilterAnalyser();
		frequencyAnalyser=new FrequencyAnalyser();
		
		String maxSize=context.getProperty("trace-max-size");
		String activeTime=context.getProperty("trace-filter-active-time");
		
		try{
			timeAnalyser.setMaxTraceSize(Integer.parseInt(maxSize));
		}catch(Exception e){timeAnalyser.setMaxTraceSize(1000);}
		try{
			timeAnalyser.setTracefilterActivetime(Integer.parseInt(activeTime));
		}catch(Exception e){timeAnalyser.setTracefilterActivetime(-1);}
	
		container.registerAnalyser(METHOD_TRACE_TYPE,timeAnalyser);
		container.registerAnalyser(METHOD_TRACE_TYPE,frequencyAnalyser);
		String clazzs = context.getProperty("detect-clazzs");
		String[] classNames=getClassNames(clazzs);
		//inject trace
		injectClass(classNames);
		log.info("methodcomponent startup.");
	}
	
	private String[] getClassNames(String clazzs)
    {
        
		if (clazzs == null || "".equals(clazzs)) {
			return new String[0];
		}
        clazzs=clazzs.replaceAll("\n","");
        clazzs=clazzs.replaceAll("\r","");
        clazzs=clazzs.replaceAll("\t","");
        clazzs=clazzs.replaceAll(" ","");
        try
        {
            return clazzs.split(";");
        }catch(Exception e)
        {
            return new String[0];
        }
    }
	
	private void injectClass(String[] resourceNames) {	
		ClassEnhancer enhancer = new ClassEnhancer();
		PackageEnhancer pEnhancer = new PackageEnhancer();
		TraceLiftcycleManager container=getComponentContext().getContainer();
		
		for (int i = 0; i < resourceNames.length; i++) {

			String resourceName = resourceNames[i];
			try {
				if(resourceName.endsWith("*")){
					//inject package
					pEnhancer.createPackage(resourceName,new TraceMethodHandle(container));
				}else{
					//inject class.
					enhancer.createClass(resourceName,new TraceMethodHandle(container));
				}
			} catch (Throwable e) {
				e.printStackTrace();
				log.warn("对类/包：" + resourceName + "的监听部署失败，不过这并不影响系统的运行。");

			}

		}
	}

	public void destory() {
		timeAnalyser.clear();
		frequencyAnalyser.clear();
		TraceLiftcycleManager container=getComponentContext().getContainer();
		
		container.unregisterAnalyser(METHOD_TRACE_TYPE,timeAnalyser);
		container.unregisterAnalyser(METHOD_TRACE_TYPE,frequencyAnalyser);
		
		timeAnalyser=null;
		frequencyAnalyser=null;
	}

	public void clear() {
		timeAnalyser.clear();
		frequencyAnalyser.clear();		
	}

	public FrequencyAnalyser getFrequencyAnalyser() {
		return frequencyAnalyser;
	}

	public void setFrequencyAnalyser(FrequencyAnalyser frequencyAnalyser) {
		this.frequencyAnalyser = frequencyAnalyser;
	}

	public TimeFilterAnalyser getTimeAnalyser() {
		return timeAnalyser;
	}

	public void setTimeAnalyser(TimeFilterAnalyser timeAnalyser) {
		this.timeAnalyser = timeAnalyser;
	}

	public ComponentContext getComponentContext() {
		return componentContext;
	}
}
