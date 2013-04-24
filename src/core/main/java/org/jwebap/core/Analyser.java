package org.jwebap.core;


/**
 * �켣���������켣�ռ���TraceContainer�ڹ켣�Ĳ���������������
 * �᲻��������������㲥�¼����ɷ�������ɹ켣��ͳ�ƣ�����ͳ�ƽ��
 * @author leadyu
 * @since Jwebap 0.5
 * @date  Aug 7, 2007
 */
public interface Analyser {
	
	public void activeProcess(Trace trace);

	public void inactiveProcess(Trace trace);

	public void destoryProcess(Trace trace);

	public void clear();
}
