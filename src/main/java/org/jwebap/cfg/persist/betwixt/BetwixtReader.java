package org.jwebap.cfg.persist.betwixt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.betwixt.io.BeanReader;
import org.jwebap.cfg.exception.BeanParseException;
import org.jwebap.cfg.persist.InputSource;
import org.jwebap.util.Assert;

/**
 * ����commons-betwixt��BeanReaderʵ��
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-4-14
 */
public class BetwixtReader implements org.jwebap.cfg.persist.BeanReader {

	/**
	 * Ҫ�������ļ�
	 */
	private InputSource _input = null;

	private Class _clazz = null;

	private String _header = null;

	/**
	 * betwixt��beanReader
	 */
	private BeanReader _reader = null;

	public BetwixtReader(String header, Class clazz, InputSource input) {
		Assert.assertNotNull(input, "null input.");
		_reader = new BeanReader();
		_clazz = clazz;
		_header = header;
		_input = input;
	}

	
	/**
	 * ��������
	 * @throws BeanParseException 
	 */
	public Object parse() throws BeanParseException {
		Object bean=null;
		InputStream in = null;
		try {
			_reader.registerBeanClass(_header, _clazz);

			in = _input.getInputStream();
			bean = _reader.parse(in);

			return bean;

		} catch (Exception e) {
			throw new BeanParseException("bean parse error:" + _input, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}

	}

}
