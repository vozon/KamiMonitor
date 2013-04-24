package org.jwebap.core;

import java.util.HashMap;
import java.util.Map;

import org.jwebap.cfg.model.ComponentDef;
import org.jwebap.cfg.model.JwebapDef;
import org.jwebap.cfg.persist.PersistManager;

/**
 * �����������ĸ�,����һ��jwebapʵ��
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date Aug 7, 2007
 */
public class RuntimeContext implements Context {

	private TraceLiftcycleManager _container = null;

	private JwebapDef _config=null;
	
	private Map _components = null;

	private ContextFactory _contextFactory=null;
	
	private PersistManager _jwebapDefManager=null;
	
	public RuntimeContext(JwebapDef config,TraceLiftcycleManager container,PersistManager defManager,ContextFactory factory) {
		_components = new HashMap();
		_container = container;
		_contextFactory=factory;
		_config=config;
		_jwebapDefManager=defManager;
	}

	/**
	 * ���ص�ǰʵ��jwebap����
	 * @return
	 */
	public JwebapDef getJwebapDef(){
		return _config;
	}
	
	/**
	 * ����jwebap���ù�����
	 * @return
	 */
	public PersistManager getJwebapDefManager(){
		return _jwebapDefManager;
	}
	
	public void registerComponent(String name, ComponentDef def)
			throws RegisterException {

		String className = def.getType();

		Class clazz;
		Component component;
		try {
			clazz = Class.forName(className);
			component = (Component) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RegisterException("component not found:" + className, e);
		} catch (InstantiationException e) {
			throw new RegisterException("component initial error:" + className, e);
		} catch (IllegalAccessException e) {
			throw new RegisterException("can't access constructor:" + className, e);
		}

		ComponentContext context = _contextFactory.createComponentContext(getContainer(),this,def);

		component.startup(context);
		_components.put(name, component);
		context.setComponent(component);
		def.setContext(context);
	}

	public Component getComponent(String name) {
		return (Component) _components.get(name);
	}

	public void unregisterComponent(String name) {
		Component component = (Component) _components.get(name);
		component.destory();
		_components.remove(name);
	}

	public TraceLiftcycleManager getContainer() {
		return _container;
	}

	public void setContainer(TraceLiftcycleManager container) {
		this._container = container;
	}

	/**
	 * RuntimeContext������������ĵĸ�
	 */
	public Context getParent() {
		return null;
	}
}
