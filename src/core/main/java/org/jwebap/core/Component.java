package org.jwebap.core;


/**
 * �������ӿڣ�ʵ�����Ϳ��Լ�����Jwebap���֮��
 * @author leadyu
 * @since Jwebap 0.5
 * @date  Aug 7, 2007
 */
public interface Component {
	
	/**
	 * plug-in����������ʵ���߿����ڸ÷����г�ʼ�����Ҫ����Դ
	 * �÷�����Jwebap����ʱ����
	 * @param context
	 */
	public void startup(ComponentContext context);
	
	/**
	 * �÷�����Jwebap�Ƴ����ʱ���ã�ʵ���߿��������������Դ�Ļ���
	 *
	 */
	public void destory();
	
	/**
	 * �÷���ͨ�����û��ڽ��津�����ã���������һЩ����ڲ�ʹ�õ���ʱ���ݣ��Լ�ͳ�����ݣ�
	 * ������ɣ������״̬Ӧ�ù�Ϊ�ո�����ʱ��״̬
	 *
	 */
	public void clear();
	
	/**
	 * ������������
	 *
	 */
	public ComponentContext getComponentContext();
}
