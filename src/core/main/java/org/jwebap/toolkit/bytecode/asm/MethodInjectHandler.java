package org.jwebap.toolkit.bytecode.asm;

import java.lang.reflect.Method;

/**
 * ����around�ķ�ʽ���ط������÷�ʽΪĬ�����ط�ʽҲ����ͨ�õķ�ʽ
 * @author leadyu
 */
public interface MethodInjectHandler {
	Object invoke(Object target, Method method, Method methodProxy, Object[] args)throws Throwable;

}
