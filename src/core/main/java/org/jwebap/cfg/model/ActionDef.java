package org.jwebap.cfg.model;

/**
 * Action���ö���
 * 
 * ��component���Բ�Ϊ��ʱ,actionʵ������ComponentContext
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-14
 */
public class ActionDef {
	
	/**
	 * ��Ӧ��Component���ƣ�Ϊ�յĻ�ActionContext���ɻ��Component�����ĵ�
	 */
	private String component = null;

	/**
	 * Action��ӳ��path
	 */
	private String path = null;

	/**
	 * Action ��Ӧ������
	 */
	private String type = null;

	/**
	 * Action��Ӧ��ģ��
	 */
	private String template = null;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 * �ж�action�����Ƿ���ȣ�actionName�������ȣ����actionNameΪ�������
	 */
	public boolean equals(Object obj){
		
		if(!(obj instanceof ActionDef)){
			return false;
		}
		ActionDef def=(ActionDef)obj;
		
		if(this.getPath()==null){
			return false;
		}else if(this.getPath().equals(def.getPath())){
			return true;
		}

		return false;
	}
	
	/**
	 * ��дsuper.hashCode
	 */
	public int hashCode(){
		if(this.getPath()==null){
			return super.hashCode();
		}else {
			return this.getPath().hashCode();
		}
	}

}
