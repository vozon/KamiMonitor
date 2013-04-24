package org.jwebap.plugin.tracer.method;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.core.TraceLiftcycleManager;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandler;

/**
 * �����������õ�handle����MethodComponent�ж����õİ��Լ���ķ������ý��м���
 * @author leadyu
 * @since Jwebap 0.5
 * @date  2007-8-16
 */
public class TraceMethodHandle implements MethodInjectHandler {
	
	private static Log log = LogFactory.getLog(TraceMethodHandle.class);
	
	/**
	 * ���й켣����
	 */
	private  transient TraceLiftcycleManager _container=null;
	
	public TraceMethodHandle(TraceLiftcycleManager container) {
		_container=container;
	}

	private TraceLiftcycleManager getContainer(){
		return _container;
	}
	
	public Object invoke(Object target, Method method, Method methodProxy, Object[] args)throws Throwable{
		Object o;
		MethodTrace trace = null;

		try {
			try {
				trace = new MethodTrace(target, method, args);
				getContainer().activateTrace(MethodComponent.METHOD_TRACE_TYPE,trace);
				
				//��ʼ�����켣�����ݿ����
				if (trace != null) {
					MethodOpenedConnectionListener.addDetectSeed(trace);
				}

			} catch (Throwable e) {
				log.warn(e.getMessage());
			}
			
			o = methodProxy.invoke(target, args);
			
			//System.out.println("detect sucess:"+method.getName());
		}catch(InvocationTargetException e){
			//�׳�ԭ���쳣
			throw e.getCause(); 
		}finally {
			try {
				getContainer().inactivateTrace(MethodComponent.METHOD_TRACE_TYPE,trace);
				MethodOpenedConnectionListener.removeDetectSeed(trace);
			} catch (Throwable e) {
				log.warn(e.getMessage());
			}
		}

		return o;

	}

}

