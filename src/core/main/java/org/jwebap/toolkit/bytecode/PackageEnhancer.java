package org.jwebap.toolkit.bytecode;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.toolkit.bytecode.asm.ASMInjectorStrategy;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandler;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandlerFactory;
import org.jwebap.toolkit.bytecode.asm.StaticHandleFactory;

/**
 * ��ע����ǿ�� �԰������������ע�룬�Ժ���Ҫ�Ľ����࣬�ṹ���㷨��û�ܺõ����
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date Aug 26, 2007
 */
public class PackageEnhancer {

	private static final Log log = LogFactory.getLog(PackageEnhancer.class);

	protected ClassEnhancer classEnhancer = null;

	public PackageEnhancer() {
		classEnhancer = new ClassEnhancer();
	}

	/**
	 * �԰������е�������ֽ�����ǿ�����ǲ�ָ��handle����������ʱָ��(����StaticHandleFactory����handleע��)
	 * 
	 * @param pckName
	 * @return
	 * @throws IOException
	 * @see StaticHandleFactory
	 */
	public Class[] createPackage(String pckName) throws InjectException {
		return createPackage(null, pckName);

	}

	public Class[] createPackage(ClassLoader definedLoader, String pckName)
			throws InjectException {
		return createPackage(definedLoader, pckName, null);
	}

	/**
	 * �԰������е�������ֽ�����ǿ
	 * 
	 * @param pckName
	 *            ����
	 * @param factory
	 *            handle����
	 * @return
	 * @throws IOException
	 */
	public Class[] createPackage(String pckName,
			MethodInjectHandlerFactory factory) throws InjectException {
		return createPackage(null, pckName, factory);
	}

	public Class[] createPackage(ClassLoader definedLoader, String pckName,
			MethodInjectHandlerFactory factory) throws InjectException {
		Class[] cls = null;

		/**
		 * ����ASM�����ֽ������ɲ��ԣ�����嵽JVM��,�ô��ǻ���jdk14
		 */
		InjectorStrategy st = new ASMInjectorStrategy(definedLoader, false);

		cls = st.injectPackage(pckName, factory);

		return cls;
	}

	public Class[] createPackage(String pckName, MethodInjectHandler handle)
			throws InjectException {
		return createPackage(pckName, handle, null);
	}

	public Class[] createPackage(String pckName, MethodInjectHandler handle,
			ClassLoader definedLoader) throws InjectException {
		Class[] cls = null;

		/**
		 * ����ASM�����ֽ������ɲ��ԣ�����嵽JVM��,�ô��ǻ���jdk14
		 */
		InjectorStrategy st = new ASMInjectorStrategy(definedLoader, false);

		cls = st.injectPackage(pckName, handle);

		return cls;
	}

}
