package org.jwebap.cfg.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * plugin���ö���
 * 
 * plugin�����ô����jar����Meta-INFĿ¼��plugin.xml��,����*componentDef,*dispatcherDef,*actionDef
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-4-6
 */
public class PluginDef {
	/**
	 * �ַ�������
	 */
	private List dispatcherDefs = null;
	
	/**
	 * plug-in�������
	 */
	private List componentDefs = null;

	private List actionDefs = null;

	public PluginDef() {
		dispatcherDefs = new ArrayList();
		componentDefs = new ArrayList();
		actionDefs = new ArrayList();
	}

	public void addComponentDef(ComponentDef component) {
		componentDefs.add(component);
	}
	
	/**
	 * �������Ʒ����������
	 * @param name
	 * @return
	 */
	public ComponentDef getComponentDef(String name){
		for(int i=0;i<componentDefs.size();i++){
			ComponentDef def=(ComponentDef)componentDefs.get(i);
			if(def.getName()!=null && def.getName().equals(name)){
				return def;
			}
		}
		return null;
	}
	
	public Collection getComponentDefs() {
		return componentDefs;
	}

	public void addDispatcherDef(DispatcherDef dispatcher) {
		dispatcherDefs.add(dispatcher);
	}

	/**
	 * �������Ʒ��طַ�������
	 * @param name
	 * @return
	 */
	public DispatcherDef getDispatcherDef(String name){
		for(int i=0;i<dispatcherDefs.size();i++){
			DispatcherDef def=(DispatcherDef)dispatcherDefs.get(i);
			if(def.getName()!=null && def.getName().equals(name)){
				return def;
			}
		}
		return null;
	}
	
	public Collection getDispatcherDefs() {
		return dispatcherDefs;
	}

	public void addActionDef(ActionDef action) {
		actionDefs.add(action);
	}

	public Collection getActionDefs() {
		return actionDefs;
	}

	/**
	 * �������Ʒ���action����
	 * @param name
	 * @return
	 */
	public ActionDef getActionDef(String path){
		for(int i=0;i<actionDefs.size();i++){
			ActionDef def=(ActionDef)actionDefs.get(i);
			if(def.getPath()!=null && def.getPath().equals(path)){
				return def;
			}
		}
		return null;
	}
	
}
