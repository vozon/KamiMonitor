package org.jwebap.toolkit.bytecode;

import org.jwebap.toolkit.bytecode.asm.ASMInjectorStrategy;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandler;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandlerFactory;
import org.jwebap.toolkit.bytecode.asm.StaticHandleFactory;


/**
 * ��ע����ǿ��
 * @author leadyu
 * @since Jwebap 0.5
 * @date  2007-9-7
 */
public class ClassEnhancer {

	/**
	 * �������ֽ�����ǿ�����ǲ�ָ��handle����������ʱָ��(����StaticHandleFactory����handleע��)
	 * @param oriClassName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InjectException
	 * @see StaticHandleFactory
	 */
	public  Class createClass(String oriClassName) throws ClassNotFoundException, InjectException{
		return createClass(null,oriClassName,null,false);
	}
	
	public Class createClass(ClassLoader definedLoader,String oriClassName) throws ClassNotFoundException, InjectException{
		return createClass(definedLoader,oriClassName,null,false);
	}
	
	/**
	 * �����ľ�̬ע��
	 * 
	 * @param definedLoader		ָ��ע���ClassLoader
	 * @param oriClassName		����
	 * @param factory			handle����
	 * @param injectSuper		�Ƿ�Ҫע�����и���ĸ���
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InjectException
	 */
	public Class createClass(String oriClassName,MethodInjectHandlerFactory factory,ClassLoader definedLoader,boolean injectSuper) throws ClassNotFoundException, InjectException{
		
		Class cls=null;
		
		/**
		 * ����ASM�����ֽ������ɲ��ԣ�����嵽JVM��,�ô��ǻ���jdk14
		 * @todo ʵ�ֻ���jdk15��instrument���ƽ���ע��
		 */
		InjectorStrategy st=new ASMInjectorStrategy(injectSuper);
		
		cls=st.inject(oriClassName,factory);
		
		return cls;
	}
	
	public  Class createClass(String oriClassName,MethodInjectHandlerFactory factory) throws ClassNotFoundException, InjectException{
		return createClass(oriClassName,factory,false);
	}
	
	/**
	 * �������ֽ�����ǿ
	 * @param oriClassName ����
	 * @param factory	handle����
	 * @param injectSuper	�Ƿ�ע�븸��
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InjectException
	 */
	public  Class createClass(String oriClassName,MethodInjectHandlerFactory factory,boolean injectSuper) throws ClassNotFoundException, InjectException{
		return createClass(oriClassName,factory,null,injectSuper);
	}
	
	public  Class createClass(ClassLoader definedLoader,String oriClassName,MethodInjectHandlerFactory factory) throws ClassNotFoundException, InjectException{
		
		return createClass(oriClassName,factory,definedLoader,false);
	}
	
	public  Class createClass(String oriClassName,MethodInjectHandler handle) throws ClassNotFoundException, InjectException{
		return createClass(oriClassName,handle,false);
	}
	
	/**
	 * �������ֽ�����ǿ
	 * @param oriClassName ����
	 * @param handle	����handle
	 * @param injectSuper	�Ƿ�ע�븸��
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InjectException
	 */
	public  Class createClass(String oriClassName,MethodInjectHandler handle,boolean injectSuper) throws ClassNotFoundException, InjectException{
		return createClass(null,oriClassName,handle,injectSuper);
	}
	
	public  Class createClass(ClassLoader definedLoader,String oriClassName,MethodInjectHandler handle,boolean injectSuper) throws ClassNotFoundException, InjectException{
		Class cls=null;
		
		/**
		 * ����ASM�����ֽ������ɲ��ԣ�����嵽JVM��,�ô��ǻ���jdk14
		 * @todo ʵ�ֻ���jdk15��instrument���ƽ���ע��
		 */
		InjectorStrategy st=new ASMInjectorStrategy(definedLoader,injectSuper);
		
		cls=st.inject(oriClassName,handle);
		
		return cls;
		
	}
	
	public  Class createClass(ClassLoader definedLoader,String oriClassName,MethodInjectHandler handle) throws ClassNotFoundException, InjectException{
		return createClass(definedLoader,oriClassName,handle,false);
	}
	
}
