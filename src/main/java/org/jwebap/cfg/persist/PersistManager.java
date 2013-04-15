package org.jwebap.cfg.persist;

import java.io.IOException;
import java.net.URL;

import org.jwebap.cfg.exception.BeanWriteException;
import org.jwebap.cfg.exception.JwebapDefNotFoundException;
import org.jwebap.cfg.exception.JwebapDefParseException;
import org.jwebap.cfg.model.JwebapDef;
import org.jwebap.util.Assert;

/**
 * ���ù�����������õļ��غͳ־û�����״̬
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2007-12-5
 */
public class PersistManager {

	private BeanFactory _factory = null;

	private final String _header = "jwebap";

	private InputSource _input = null;

	public PersistManager(InputSource input) {
		_factory = new BeanFactory();
		_input=input;
	}

	public PersistManager(URL url) {
		_factory = new BeanFactory();
		_input=new InputSource(url);
	}
	
	/**
	 * �־û�jwebap����
	 * 
	 * @param def
	 * @throws JwebapDefNotFoundException 
	 * @throws BeanWriteException 
	 */
	public void save(JwebapDef def) throws JwebapDefNotFoundException, BeanWriteException {
		Assert.assertNotNull(def, "jwebap def is null.");
		BeanWriter writer = null;
		try {
			writer = _factory.createWriter(_header, _input);
			writer.write(def);
		} catch (IOException e) {
			throw new BeanWriteException("",e);
		}

	}

	/**
	 * ����jwebap����
	 * 
	 * @param path
	 * @return
	 * @throws JwebapDefNotFoundException 
	 * @throws JwebapDefParseException 
	 */
	public JwebapDef get() throws JwebapDefNotFoundException, JwebapDefParseException {
		try {
			BeanReader reader = _factory.createReader(_header, JwebapDef.class,_input);
			return (JwebapDef) reader.parse();
		} catch (Exception e) {
			throw new JwebapDefParseException("jwebap.xml parse fail :"
					+ e.getMessage(), e);
		}
	}

}
