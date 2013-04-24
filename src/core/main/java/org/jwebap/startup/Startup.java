package org.jwebap.startup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.cfg.model.ComponentDef;
import org.jwebap.cfg.model.JwebapDef;
import org.jwebap.cfg.persist.PersistManager;
import org.jwebap.core.ContextFactory;
import org.jwebap.core.RuntimeContext;

/**
 * Jwebap������
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date 2007-8-16
 */
public class Startup {

	private static final Log log = LogFactory.getLog(Startup.class);

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
	 * @param args
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
