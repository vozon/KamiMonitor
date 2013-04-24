package org.jwebap.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ��������ʵ����
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2008-10-6
 */
public class ParameterStorage implements ParameterMap{
	/**
	 *����Map 
	 */
	private Map paramMap=new HashMap();
	
	public ParameterStorage(Map map){
		paramMap=map;
	}
	
	public ParameterStorage(ParameterMap map) {
		String[] names=map.propNames();
		for(int i=0;i<names.length;i++){
			putProperty(names[i],map.getProperty(names[i]));
		}
	}
	
	protected Map getParamMap() {
		return paramMap;
	}

	public ParameterStorage(){
		paramMap=new HashMap();
	}
	
	/**
	 * ���ز���ֵ���Ҳ���ʱ���ؿմ�
	 */
	public String getProperty(String key) {
		String value=(String)paramMap.get(key);
		return value==null?"":value;
	}

	/**
	 * ���ò���
	 */
	public void putProperty(String key, String value) {
		paramMap.put(key,value);
	}
	
	/**
	 * �������в�����
	 */
	public String[] propNames(){
		String[] keys=new String[paramMap.size()];
		Set keySet=paramMap.keySet();
		keySet.toArray(keys);
		return keys;
	}

}