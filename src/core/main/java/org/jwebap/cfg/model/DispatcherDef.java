package org.jwebap.cfg.model;

import java.util.HashMap;

import org.jwebap.ui.controler.Dispatcher;
import org.jwebap.ui.controler.Mapper;
import org.jwebap.util.ParameterStorage;

/**
 * ��ͼ�ַ������ö���
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-4-6
 */
public class DispatcherDef extends ParameterStorage {

	/**
	 * Component��ʵ���࣬����org.jwebap.http.HttpComponent
	 */
	private String type = null;

	/**
	 * �ַ������������
	 */
	private String name = null;

	/**
	 * �ַ�����Ӧ��mapping,�������JwebapServlet���õ�mapping.����Servlet���õ�
	 * mapping=/detect���ַ������õ�mapping=/sql/view,��ô����URL=/detect/sql/view
	 * �����󣬾ͻ�ͨ���÷ַ������зַ�.
	 */
	private Mapper mapper = null;

	private String mapping = null;

	private Dispatcher dispatcher;

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		if(mapping==null){
			mapping="";
		}
		this.mapper = new Mapper(mapping);
		this.mapping = mapping;
	}

	public DispatcherDef() {
		super(new HashMap());
	}

	public String getType() {
		return type;
	}

	public void setType(String className) {
		this.type = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * �ж�dispatcher�����Ƿ���ȣ�dispatcherName�������ȣ����dispatcherNameΪ�������
	 */
	public boolean equals(Object obj){
		
		if(!(obj instanceof DispatcherDef)){
			return false;
		}
		DispatcherDef def=(DispatcherDef)obj;
		
		if(this.getName()==null){
			return false;
		}else if(this.getName().equals(def.getName())){
			return true;
		}

		return false;
	}
	
	/**
	 * ��дsuper.hashCode
	 */
	public int hashCode(){
		if(this.getName()==null){
			return super.hashCode();
		}else {
			return this.getName().hashCode();
		}
	}
	
}
