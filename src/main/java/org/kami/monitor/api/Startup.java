package org.kami.monitor.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.kami.monitor.api.cfg.model.ComponentDef;
import org.kami.monitor.api.cfg.model.JwebapDef;
import org.kami.monitor.api.cfg.persist.PersistManager;
import org.kami.monitor.api.core.ContextFactory;
import org.kami.monitor.api.core.RuntimeContext;

/**
 * Jwebap������
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date 2007-8-16
 */
public class Startup {

	private static final Logger log = LoggerFactory.getLogger(Startup.class);

	/**
	 * Jwebap����ʱ������������Jwebap����ʱ����
	 * 
	 * Ŀǰ��Jwebap����һ��Application��˵�ǵ�ʵ����
	 */
	private static RuntimeContext _context = null;
	// TODO ��RumtimeContextFactory����jwebapʵ��
	
	/**
	 * ����Jwebap��ǰʵ��
	 */
	public static RuntimeContext getRuntimeContext(){
		if(_context==null){
			throw new JwebapInitialException("jwebap not startup successfully,please check the application log.");
		}
		return _context;
	}
	
	/**
	 * Jwebap��������,���ļ�·������jwebap
	 * 
	 * @param args
	 * @throws MalformedURLException 
	 * @throws StartupException
	 */
	public static void startup(String path) throws MalformedURLException  {
		URL url=new URL("file:"+path);
		startup(url);
	}
	
	/**
	 * Jwebap��������,��URL����,����ֻ��,����֤��д
	 * 
	 * @param url the path of the kami-monitor-api.xml
	 * @throws StartupException
	 */
	public static void startup(URL url)  {
		
		ContextFactory contextFactory=new ContextFactory();
		JwebapDef config = null;
		PersistManager defManager=new PersistManager(url);
		
		try {
			config = defManager.get();
		} catch (Exception e) {
			throw new JwebapInitialException("",e);
		}
		

		RuntimeContext context = contextFactory.createRuntimeContext(config,defManager);
		Collection components = config.getAllComponentDefs();
		Iterator componentIt=components.iterator();
		/**
		 * ע��Components
		 */
		while (componentIt.hasNext()) {
			ComponentDef def = (ComponentDef)componentIt.next();

			try {
				context.registerComponent(def.getName(), def);
			} catch (Exception e) {
				log.error("register component:" + def.getType() + " fail."
						+ e.getMessage());
				e.printStackTrace();
			}

		}
		//Startup.getRuntimeContext()���Լ��jwebap�Ƿ���������������������RuntimeContext��ֵ
		_context=context;
		log.info("jwebap component startup.");

	}
}
