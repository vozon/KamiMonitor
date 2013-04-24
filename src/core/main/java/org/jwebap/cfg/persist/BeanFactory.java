package org.jwebap.cfg.persist;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jwebap.cfg.persist.betwixt.BetwixtReader;
import org.jwebap.cfg.persist.betwixt.BetwixtWriter;

/**
 * java bean����
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-4-13
 */
public class BeanFactory {
	
	
	/**
	 * ����BeanReader
	 * @param input ���������ļ�
	 * @return
	 */
	public BeanReader createReader(String header,Class clazz,InputSource input){
		BetwixtReader reader=new BetwixtReader(header,clazz,input);
		return reader;
	}
	
	/**
	 * ����BeanWriter
	 * @param output ���
	 * @return
	 * @throws IOException 
	 */
	public BeanWriter createWriter(String header,InputSource input) throws IOException{
		BetwixtWriter writer=new BetwixtWriter(header,input);
		return writer;
	}
	
}
