package org.jwebap.util;

/**
 * Bean����������
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-24
 */
public class BeanUtil {

	/**
	 * ���ݵ�ǰClassLoader������ʼ����ʵ��,�����ǲ���Class.forName
	 * @param clazzName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object newInstance(String clazzName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clz = loader.loadClass(clazzName);
		return clz.newInstance();
	}
	
	
}
