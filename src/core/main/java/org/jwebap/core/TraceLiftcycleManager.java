package org.jwebap.core;


/**
 * �켣�ռ�������������ƹغ��������������
 * @author leadyu
 * @since Jwebap 0.5
 * @date  2007-8-8
 */
public interface TraceLiftcycleManager {
	public void activateTrace(Object traceType,Trace trace);
	public void inactivateTrace(Object traceType,Trace trace);
	public void destoryTrace(Object traceType,Trace trace);
	public void registerAnalyser(Object traceType,Analyser analyser);
	public void unregisterAnalyser(Object traceType,Analyser analyser);

}
