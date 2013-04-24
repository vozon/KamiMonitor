package org.jwebap.toolkit.bytecode;

import org.jwebap.toolkit.bytecode.asm.ASMInjectorStrategy;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandler;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandlerFactory;

/**
 * �ྲ̬��ǿ����
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-3-7
 * @see ASMInjectorStrategy ����ASM��ע�����
 * @see MethodInjectHandlerFactory ����ע�빤��
 * @see MethodInjectHandler ����ע��handle
 * 
 */
public interface InjectorStrategy {

    public Class inject(String className,MethodInjectHandlerFactory factory) throws InjectException;

    public Class inject(String className,MethodInjectHandler handler) throws InjectException;

    public Class[] injectPackage(String pckName,MethodInjectHandlerFactory factory) throws InjectException;

    public Class[] injectPackage(String pckName,MethodInjectHandler handler) throws InjectException;

}
