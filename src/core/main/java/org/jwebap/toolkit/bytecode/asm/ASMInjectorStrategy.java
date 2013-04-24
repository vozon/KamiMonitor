package org.jwebap.toolkit.bytecode.asm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.asm.ClassReader;
import org.jwebap.asm.ClassVisitor;
import org.jwebap.asm.ClassWriter;
import org.jwebap.toolkit.bytecode.InjectException;
import org.jwebap.toolkit.bytecode.InjectorStrategy;
import org.jwebap.util.Assert;

/**
 * ���ֽ�������޸ģ�����asm2.1���
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date 2007-9-7
 */
public class ASMInjectorStrategy implements InjectorStrategy {

	protected static final String METHOD_POSTFIX = "_$proxy";

	protected static final String HANDLER_PREFIX = "handle$";

	protected static final String METHOD_PREFIX = "method$";

	protected static final String METHOD_PROXY_PREFIX = "methodProxy$";
 
	private static final Log log = LogFactory.getLog(ASMInjectorStrategy.class);

	/**
	 * ����Ѱ������Դ��loader
	 */
	private ClassLoader resourceLoader = null;

	/**
	 * ָ��loader����ע��
	 */
	private ClassLoader definedLoader = null;

	/**
	 * �Ƿ�Ҫע�븸��
	 */
	private boolean injectSuper = false;


	public ASMInjectorStrategy(boolean injectSuper) {
		this(null,injectSuper);
	}

	public ASMInjectorStrategy(ClassLoader definedLoader, boolean injectSuper) {
		this.definedLoader = definedLoader;
		// Ĭ�ϲ���������ClassLoader����������this.class.getClassLoader()
		resourceLoader = Thread.currentThread().getContextClassLoader();
		this.injectSuper = injectSuper;
	}

	private static Method findResource = null;

	private static Method defineClass = null;

	static {
		ini();
	}

	private static void ini() {
		Class ll = null;
		try {
			ll = Class.forName("java.lang.ClassLoader");
		} catch (ClassNotFoundException e) {
		}

		try {
			findResource = ll.getDeclaredMethod("findResource",
					new Class[] { String.class });
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		findResource.setAccessible(true);

		try {
			defineClass = ll.getDeclaredMethod("defineClass", new Class[] {
					String.class, byte[].class, Integer.TYPE, Integer.TYPE });
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		defineClass.setAccessible(true);
	}

	/**
	 * �ྲ̬ע�룬��ע��handle����
	 */
	public Class inject(String className,MethodInjectHandlerFactory factory) throws InjectException {	
		return injectInternal(className,factory);
	}
	
	/**
	 * �ྲ̬ע�룬��ע��handle����
	 */
	public Class inject(String className,MethodInjectHandler handle) throws InjectException {
		return injectInternal(className,handle);
	}
	
	
	private void injectHandle(String className,Object hanldeObject){
		//ע��ע�����
		if(hanldeObject!=null && hanldeObject instanceof MethodInjectHandler){
			StaticHandleFactory.registerHandle(className,(MethodInjectHandler)hanldeObject);				
		}	
		//ע��ע�����
		if(hanldeObject!=null && hanldeObject instanceof MethodInjectHandlerFactory){
			StaticHandleFactory.registerFactory(className,(MethodInjectHandlerFactory)hanldeObject);				
		}
	}
	/**
	 * �ྲ̬��ǿ
	 */
	private Class injectInternal(String className,Object hanldeObject) throws InjectException {
		
		Class clazz = null;
		try {
			Assert.assertNotNull(this.resourceLoader, "resourceLoaderΪ��.");
			
			injectHandle(className,hanldeObject);
			
			clazz = defineClass(className, this.resourceLoader,
					this.definedLoader,hanldeObject);
		} catch (ClassNotFoundException e) {
			throw new InjectException(className + "ע��ʧ��.", e);
		} catch (GenBytecodeException e) {
			throw new InjectException(className + "ע��ʧ��.", e);
		} catch (DefineBytecodeException e) {
			throw new InjectException(className + "ע��ʧ��.", e);
		}
		return clazz;

	}

	/**
	 * �����ֽ���
	 * 
	 * @param rawClassData
	 * @return
	 * @throws Exception
	 */
	public Type genByteCode(byte[] rawClassData) throws Exception {

		ClassReader reader = new ClassReader(rawClassData);
		ClassWriter classWriter = new ClassWriter(true);
		ClassVisitor target = classWriter;

		ClassInitClassVisitor classInitClassVisitor = new ClassInitClassVisitor(
				target);
		InjectClassVisitor injectClassVisitor = new InjectClassVisitor(
				classInitClassVisitor);
		ASMClassVisitor visitor = new ASMClassVisitor(target,
				injectClassVisitor);

		reader.accept(visitor, false);

		Type clazz = visitor.getType();
		clazz.setByteCode(classWriter.toByteArray());
		
		return clazz;
	}

	/**
	 * ���ø���ί�в���Ϊ�޸ĵ���Ѱ������ʵ�ClassLoader���м��أ�������֤
	 * ���ϸ���ί�в��Ե�CloassLoaderģ���ܹ���ȷ�ļ����࣬��֤������ģ���� ���ڶ���汾��ͬ���ࡣ
	 * �����������м����֧����ã���Ӧ���ӵĲ���ģ�ͣ�����ͬһ��EAR�е�EJB,������Щ���������ֲ��Ե��м�������������⡣
	 * 
	 * @throws ClassNotFoundException
	 * @throws DefineBytecodeException
	 * @throws GenBytecodeException
	 */
	private Class defineClass(String clazz, ClassLoader resourceLoader,
			ClassLoader definedLoader,Object hanldeObject) throws ClassNotFoundException,
			GenBytecodeException, DefineBytecodeException {

		if(resourceLoader==null){
			throw new ClassNotFoundException(clazz);
		}
		
		try {
			defineClass(clazz, resourceLoader.getParent(), definedLoader,hanldeObject);
		} catch (ClassNotFoundException fe) {
			URL url = null;
			try {
				url = (URL) findResource.invoke(resourceLoader,
						new Object[] { clazz.replace('.', '/') + ".class" });
				
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			if (url == null) {
				throw new ClassNotFoundException(clazz + " not found.");
			} else {
				return defineClass(clazz, url,
						definedLoader == null ? resourceLoader : definedLoader,hanldeObject);
			}
		}

		return null;
	}

	private Class defineClass(String className, URL url,
			ClassLoader definedLoader,Object hanldeObject) throws ClassNotFoundException,
			GenBytecodeException, DefineBytecodeException {

		Assert.assertNotNull(definedLoader, "definedLoaderΪ��.");

		InputStream is = null;
		try {
			is = url == null ? null : url.openStream();
		} catch (IOException e) {
		}

		if (is == null) {
			throw new ClassNotFoundException("class resource:" + url
					+ " can't open.");
		}

		byte[] b = null;
		try {
			b = new byte[is.available()];

			// �趨buffer��С
			int n = 512;
			byte buffer[] = new byte[n];
			int pos = 0;
			// ��ȡ���ļ��������ֽ���
			while (n > 0) {
				int t = is.read(buffer, 0, n);
				if (t <= -1) {
					break;
				}
				System.arraycopy(buffer, 0, b, pos, t);
				pos += t;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Type type=null;
		try {
			type = genByteCode(b);
			// debug���������ɵ��ֽ���
			// FileOutputStream file=new
			// FileOutputStream("d:/jad/"+className.substring(className.lastIndexOf('.')+1)+".class");
			// file.write(r);
			// file.flush();
			// file.close();
		} catch (Throwable e) {
			throw new GenBytecodeException(e.getMessage(), e);
		}

		String superClz=type==null?null:type.getSuperName();
		
		//���injectSuper=true,��ע�븸��
		if(injectSuper==true){
			if(superClz!=null){
				if(!(superClz.startsWith("java/") || superClz.startsWith("javax/") || superClz.startsWith("sun/"))){
					try {
						injectInternal(superClz.replace('/', '.'),hanldeObject);
					} catch (InjectException e) {
						log.warn("��ע����"+className+"ʱ�Ը���:"+superClz+"��ע�����");
						e.printStackTrace();
					}
				}
			}
		}
		
		Object clazz = null;

		try {
			clazz = defineClass.invoke(definedLoader, new Object[] { className,
					type.getByteCode(), new Integer(0), new Integer(type.getByteCode().length) });
		} catch (Throwable e) {
			throw new DefineBytecodeException(type+"ע�����:"+e.getCause().getMessage(), e.getCause());
		}

		return (Class) clazz;
	}

	public String toString() {
		return "ASMInjectorStrategy(uses http://asm.objectweb.org)";
	}

	/**
	 * ��ע���ڲ�ʵ���࣬���ڸ��ݰ���Ѱ�Ұ������е���
	 * @author leadyu(yu-lead@163.com)
	 * @since Jwebap 0.5
	 * @date  Mar 7, 2008
	 */
	private abstract class DoProcess{
		Class[] doProcess(String pckName,Object[] args) throws InjectException{
			ClassLoader loader=Thread.currentThread().getContextClassLoader();
			String resourceName=pckName.substring(0,pckName.length()-1).replace('.','/');
			Enumeration urls;
			try {
				urls = loader.getResources(resourceName);
			} catch (IOException e) {
				throw new InjectException(resourceName+"������.",e);
			}
			List cls=new ArrayList();
			ArrayList clf=new ArrayList();
			int failCount=0;
			while(urls.hasMoreElements()){
				URL url=(URL)urls.nextElement();
				String path=url.getFile();
				File dir=new File(path);
				if(path.indexOf(".jar")>0){
					String jarPath=path.substring(0,path.indexOf(".jar"))+".jar";
					if(jarPath.indexOf("file:/")>-1){
						jarPath=jarPath.substring(jarPath.indexOf("file:/")+6);
					}
					File jarFile=new File(jarPath);
					if(jarFile.exists()){
						JarFile jar;
						try {
							jar = new JarFile(jarFile);
						} catch (IOException e) {
							throw new InjectException(jarFile+"��ȡ����ȷ.",e);
						}
						Enumeration entries=jar.entries();
						
						
						while(entries.hasMoreElements()){
							JarEntry entry=(JarEntry)entries.nextElement();
							if(!entry.isDirectory()){
								String name=entry.getName();
								if(name!=null && name.indexOf(".class")>-1 && name.indexOf(resourceName)>-1){
									String className=name.substring(0,name.indexOf(".class")).replace('/','.');
									if(!clf.contains(className)){
										clf.add(className);
									}
									
								}
							}
						}
							
						
					}
					
				}else if(dir.exists()){
					String[] files=dir.list();
					for(int i=0;i<files.length;i++){
						if(!files[i].endsWith(".class")){
							continue;
						}
						try {
							String className = pckName.substring(0, pckName
									.length() - 1)
									+ files[i].substring(0, files[i]
											.lastIndexOf('.'));

							if(!clf.contains(className)){
								clf.add(className);
							}
							
						} catch (Exception e) {
							log.warn("className illegal:"+pckName);
						}
						
						
					}
				}
				
			}
			
			//inject each class in files
			for(int i=0;i<clf.size();i++){
				Class clazz=null;
				String className = (String)clf.get(i);
				try {
					clazz = doInject(className, args);
					log.debug("inject class:"+className);
				} catch (Exception e) {
					log.warn(className+"ע��ʧ��"+e.getMessage()+",�����ⲻӰ��԰���ע��.");
					e.printStackTrace();
				}
				
				if(clazz!=null){
					cls.add(clazz);
				}else{
					failCount++;
				}
				
			}
			
			log.debug("inject total "+cls.size()+"class.fail "+failCount);
			Class[] cs=new Class[]{};
			if(cls.size()>0){
				cls.toArray(cs);
			}
			return cs;
		}
		
		abstract Class doInject(String className,Object[] args) throws ClassNotFoundException, InjectException;
	};
	
	public Class[] injectPackage(String pckName, MethodInjectHandlerFactory factory) throws InjectException {
		
		//ע�����handle
		if(factory!=null){
			StaticHandleFactory.registerPkgFactory(pckName,factory);				
		}
		DoProcess process=new DoProcess(){
			Class doInject(String className,Object[] args)throws ClassNotFoundException, InjectException{
				return injectInternal(className,null);
			}		
		};
		return process.doProcess(pckName,null);
	}

	public Class[] injectPackage(String pckName, MethodInjectHandler handle) throws InjectException {
		//ע�����handle
		if(handle!=null){
			StaticHandleFactory.registerPkgHandle(pckName,handle);				
		}
		DoProcess process=new DoProcess(){
			Class doInject(String className,Object[] args)throws ClassNotFoundException, InjectException{
				return injectInternal(className,null);
			}		
		};
		return process.doProcess(pckName,null);
	}
}

/**
 * �����ֽ������,������ASM�����µ��ֽ���ʱ���������׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date Mar 1, 2008
 */
class GenBytecodeException extends Exception {
	public GenBytecodeException(Throwable e) {
		super(e);
	}

	public GenBytecodeException(String message, Throwable e) {
		super(message, e);
	}

	public GenBytecodeException(String message) {
		super(message);
	}
}

/**
 * �ඨ����󣬵��ֽ��붨��ʱ���������׳�
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date Mar 1, 2008
 */
class DefineBytecodeException extends Exception {
	public DefineBytecodeException(Throwable e) {
		super(e);
	}

	public DefineBytecodeException(String message, Throwable e) {
		super(message, e);
	}

	public DefineBytecodeException(String message) {
		super(message);
	}
}