package org.jwebap.core;

import java.util.ArrayList;
import java.util.List;

/**
 * �켣����
 * 
 * ��Jwebap�й켣����һ����Ҫ�ĺ���ģ�͡�
 * 
 * <p>
 * �켣�Ķ���
 * 
 * �켣���ڷ�װ�����ܷ������õ���Ϣ�����������ĵ���Ϣ���̶߳�ջ����Ϣ��ִ ��ʱ�����Ϣ�ȵȣ���
 * 
 * ��ϵͳ�У��κγ����ִ�ж��п������¹켣�����ݿ�ĵ��û�����SQL�켣�� ����Ĺ켣��http��������·��ʵĹ켣������ķ���ִ��Ҳ�������¹켣����
 * 
 * �켣֮����ڹ�ϵ���������ݿ�����Ĺ켣��SQL�Ĺ켣��http������෽�� ִ�еĹ켣֮�䶼��һ���Ĺ�ϵ��Jwebap��ͨ���ֽ���ע�룬�ռ�����ʱ����
 * Ϣ����װ�ɹ켣����������(Analyser)����ͳ�ơ�
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-04-11
 */
public class Trace{

	/**
	 * ����ʱ��
	 */
	private long createdTime;

	/**
	 * ���ʱ��
	 */
	private long inActiveTime;

	/**
	 * �켣����
	 */
	private String content;

	/**
	 * ���켣
	 */
	Trace parent;

	/**
	 * �ӹ켣
	 */
	private List traces;

	public Trace() {
		traces = new ArrayList();
		init(parent);
	}

	public Trace(Trace parent) {
		traces = new ArrayList();
		init(parent);
	}

	private void init(Trace parent) {
		if (parent != null)
			parent.addChild(this);
		this.parent = parent;
		createdTime = System.currentTimeMillis();
	}

	/**
	 * �����ӹ켣
	 * 
	 * @param trace
	 */
	public synchronized void addChild(Trace trace) {
		this.traces.add(trace);
	}


	/**
	 * �õ��ӹ켣
	 * 
	 * ���ض���Ϊ������2��Ŀ��:
	 * 
	 * 1)���ظ�������֤���ڵĲ�����������켣���ڲ�����
	 * 
	 * 2)��������Ϊ���飬�����ײ������壬����Ϊ�õ����ǹ켣�ڲ��ļ��Ͻṹ
	 * 
	 * @return
	 */
	public Trace[] getChildTraces() {
		Trace[] ts = new Trace[traces.size()];
		traces.toArray(ts);
		return ts;
	}

	/**
	 * �õ�ʧЧʱ��
	 * 
	 * @return
	 */
	public long getActiveTime() {
		return getInactiveTime() <= 0L ? System.currentTimeMillis()
				- getCreatedTime() : getInactiveTime() - getCreatedTime();
	}

	/**
	 * ����ӹ켣
	 */
	public synchronized void clearChildTrace() {
		if (traces != null)
			traces.clear();
	}

	/**
	 * ɾ���ӹ켣
	 * 
	 * @param trace
	 */
	protected synchronized void removeChildTrace(Trace trace) {
		if (this.traces != null) {
			this.traces.remove(trace);
			trace.parent = null;
		}
	}

	/**
	 * ���ٹ켣
	 */
	public void destroy() {
		if (parent != null) {
			parent.removeChildTrace(this);
			parent = null;
		}
		Trace[] children = getChildTraces();
		clearChildTrace();
		for (int i = 0; i < children.length; i++) {
			Trace trace = children[i];
			trace.destroy();
		}

	}

	/**
	 * ���
	 */
	public void inActive() {
		if (!(inActiveTime > 0L))
			inActiveTime = System.currentTimeMillis();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getInactiveTime() {
		return inActiveTime;
	}

	public long getCreatedTime() {
		return createdTime;
	}

}
