package org.jwebap.core;

/**
 * �����Ľӿ�
 * <p>
 * ʵ��һ�������Ĳ㼶��������һЩ�����Ƿǳ����õģ����磺
 * 1)��Component��ʼ��ʱ��Ӧ�õ�����������ȫ�������ĵ�һ���ӡ�
 * 2)��ͼ�����ͨ��Component��RuntimeContext��ȡ��ͼ������������Ӧ�ü������ͨ��ȫ�ֵ������Ļ�ȡ�������Ϣ
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-4
 */
public interface Context {

	public Context getParent();
}
