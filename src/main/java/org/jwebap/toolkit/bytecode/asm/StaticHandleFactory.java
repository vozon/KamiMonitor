package org.jwebap.toolkit.bytecode.asm;

import java.util.HashMap;
import java.util.Map;

/**
 * �ֽ���ע��handle������
 * 
 * ����ǿ������ڵ�һ�α�����ʱ���ӹ����л�÷�����handle���������ھ�̬������
 * 
 * @todo ���ڶ�ע����ֽ��룬Ϊÿһ���෽����������һ��handle��̬���ԣ���handle��һ��ʱ�����һ�����ڴ��˷�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date Mar 7, 2008
 */
public class StaticHandleFactory {

	private static Map factorys = new HashMap();

	/**
	 * ��ȡ����handle
	 * 
	 * @param className
	 * @param methodName
	 * @param signature
	 * @return
	 */
	public static synchronized MethodInjectHandler getMethodHandler(
			String className, String methodName, String signature) {

		Object handleObject = factorys.get(className);
		
		/**
		 * ����������handle
		 */
		if (handleObject != null
				&& handleObject instanceof MethodInjectHandlerFactory) {
			MethodInjectHandlerFactory factory = (MethodInjectHandlerFactory) handleObject;
			return factory.getMethodHandler(className, methodName, signature);
		} else if (handleObject != null
				&& handleObject instanceof MethodInjectHandler) {
			return (MethodInjectHandler) handleObject;
		}

		/**
		 * �ð�������handle
		 */
		int last=className.lastIndexOf('.');
		
		String pkgName=last<0?"":className.substring(0,last)+".*";
		Object pkgHandle = factorys.get(pkgName);
		if (pkgHandle != null
				&& pkgHandle instanceof MethodInjectHandlerFactory) {
			MethodInjectHandlerFactory factory = (MethodInjectHandlerFactory) pkgHandle;
			return factory.getMethodHandler(className, methodName, signature);
		} else if (pkgHandle != null
				&& pkgHandle instanceof MethodInjectHandler) {
			return (MethodInjectHandler) pkgHandle;
		}
		
		return null;

	}

	/**
	 * ע����ע�빤��
	 * 
	 * @param className
	 * @param factory
	 * @see MethodInjectHandlerFactory
	 *      invokeHandle�Ĺ���,ע��Ĵ�������������ݲ�ͬ������(����:����,��������)ִ�в�ͬ��ע�����
	 */
	public static void registerFactory(String className,
			MethodInjectHandlerFactory factory) {
		factorys.put(className, factory);
	}

	/**
	 * ע����ע�빤��
	 * 
	 * @param className
	 * @param handle
	 * @see MethodInjectHandler invokeHandle,ע�������Ҫʵ�ֵĽӿ�
	 */
	public static void registerHandle(String className,
			MethodInjectHandler handle) {
		factorys.put(className, handle);
	}
	
	public static void registerPkgFactory(String pkgName,
			MethodInjectHandlerFactory factory) {
		factorys.put(pkgName, factory);
	}
	
	public static void registerPkgHandle(String pkgName,
			MethodInjectHandler handle) {
		factorys.put(pkgName, handle);
	}

}
