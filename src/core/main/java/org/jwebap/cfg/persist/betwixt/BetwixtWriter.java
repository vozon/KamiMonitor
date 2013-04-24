package org.jwebap.cfg.persist.betwixt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.ValueSuppressionStrategy;
import org.jwebap.cfg.exception.BeanWriteException;
import org.jwebap.cfg.persist.InputSource;
import org.jwebap.util.Assert;

/**
 * ����commons-betwixt��BeanWriterʵ��
 * 
 * ʵ�ְ�bean����ӳ�����д��xml
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-4-25
 */
public class BetwixtWriter implements org.jwebap.cfg.persist.BeanWriter {
	/**
	 * Ҫ�������ļ�
	 */
	private InputSource _input = null;

	private String _header = null;


	public BetwixtWriter(String header,InputSource input) throws IOException {
		_header = header;
		_input = input;

	}

	private BeanWriter createWriter(InputSource input) throws IOException {
		Assert.assertNotNull(input, "input null");
		BeanWriter writer = null;

		File file = input.getFile();
		writer = new BeanWriter(new FileWriter(file));
		writer.getBindingConfiguration().setMapIDs(false);
        //��������ֵ���Բ���
        writer.getBindingConfiguration().setValueSuppressionStrategy(ValueSuppressionStrategy.ALLOW_ALL_VALUES);
		return writer;
	}

	/**
	 * �Ѷ���д��xml�ļ�
	 */
	public void write(Object bean) throws BeanWriteException {
		try {
			BeanWriter writer = this.createWriter(_input);
	        //writer.getXMLIntrospector().register(CoreDef.class, new InputSource(new StringReader(customDotBetwixt)));
	        writer.write(_header,bean);
	        writer.close();
		} catch(IOException e) {
			throw new BeanWriteException("input source '"+_input+"' io error when write:" + e.getMessage(), e);
		}catch (Exception e) {
			throw new BeanWriteException("bean write error:" + _input, e);
		}
	}

}
