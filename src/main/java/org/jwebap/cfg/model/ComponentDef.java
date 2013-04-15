package org.jwebap.cfg.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONString;
import org.jwebap.core.ComponentContext;
import org.jwebap.util.ParameterStorage;

/**
 * Component Plug-in����
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-4
 */
public class ComponentDef extends ParameterStorage implements JSONString{

	/**
	 * Component��ʵ���࣬����org.jwebap.http.HttpComponent
	 */
	private String _type = null;

	/**
	 * ��������ģ��������ע�ᵽ����ʱ�����������setContext�Ѳ�����������ע����������Ժ������ʹ��
	 */
	private ComponentContext _context = null;
	
	/**
	 * ������������
	 */
	private String _name = null;
	
	private Map _parameters=new HashMap();

	/**
	 * �������ʵ��
	 * @author leadyu(yu-lead@163.com)
	 * @since Jwebap 0.6
	 * @date  2008-5-2
	 */
	public final static class PropertyEntry{
		private String _name;
		private String _value;
		private String _description;
		private String _style="text";
		public String getName() {
			return _name;
		}
		public void setName(String name) {
			_name = name;
		}
		public String getValue() {
			return _value;
		}
		public void setValue(String value) {
			_value = value;
		}
		public String getDescription() {
			return _description;
		}
		public void setDescription(String description) {
			_description = description;
		}
		public String getStyle() {
			return _style;
		}
		public void setStyle(String style) {
			_style=style;
		}
	}
	
	public ComponentDef() {
		super(new HashMap());
	}

	public String getType() {
		return _type;
	}

	public void setType(String className) {
		_type = className;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public ComponentContext getContext() {
		return _context;
	}
	
	/**
	 * ����������ĵ����������
	 * @param context
	 */
	public void setContext(ComponentContext context) {
		_context = context;
	}

	/**
	 * ��������
	 * @param entry
	 */
	public void putProperty(PropertyEntry entry){
		putProperty(entry.getName(),entry.getValue());
		_parameters.put(entry.getName(),entry);
	}

	/**
	 * ��ȡ��������entry
	 * @param entry
	 */
	public Collection getProperties(){		
		return _parameters.values();
	}
	
	/**
	 * ��ȡ����entry
	 * @param entry
	 */
	public PropertyEntry getPropertyEntry(String name){		
		return (PropertyEntry)_parameters.get(name);
	}
	
	/**
	 * �ж�component�����Ƿ���ȣ�componentName�������ȣ����componentNameΪ�������
	 */
	public boolean equals(Object obj){
		
		if(!(obj instanceof ComponentDef)){
			return false;
		}
		ComponentDef def=(ComponentDef)obj;
		
		if(getName()==null){
			return false;
		}else if(getName().equals(def.getName())){
			return true;
		}

		return false;
	}
	
	/**
	 * ��дsuper.hashCode
	 */
	public int hashCode(){
		if(getName()==null){
			return super.hashCode();
		}else {
			return getName().hashCode();
		}
	}
	
	/**
	 * ת����Json����
	 * @return
	 */
	public String toJSONString() {
		Map map = new HashMap();
		map.put("name", _name);
		map.put("type", _type);
		return new JSONObject(map).toString();
	}
}