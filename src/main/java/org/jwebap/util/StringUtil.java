package org.jwebap.util;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * �ַ�������������
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date  2009-3-27
 */
public class StringUtil {
	
	/**
	 * �滻�ַ�����������������ʽ��������Ҫ��ߵ������ʹ��
	 * @param line
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static final String replaceAll(String line, String str1, String str2) {
		StringBuffer newStr = new StringBuffer();
		int lastAppendIndex=0;
		
		int start=line.indexOf(str1,lastAppendIndex);
		
		if(start==-1)return line;
		
		while(start>-1){
			newStr.append(line.substring(lastAppendIndex, start));
			newStr.append(str2);
			lastAppendIndex=start+str1.length();
			start=line.indexOf(str1,lastAppendIndex);
		}
		newStr.append(line.substring(lastAppendIndex, line.length()));
		
		return newStr.toString();

	}

	/**
	 * �ָ��ַ�����������������ʽ��������Ҫ��ߵ������ʹ��
	 * @param line
	 * @param separator
	 * @return
	 */
	public static final String[] split(String line, String separator) {
		int index = 0;
		ArrayList matchList = new ArrayList();

		int start = line.indexOf(separator, index);

		if (start == -1)
			return new String[] { line.toString() };

		while (start > -1) {
			String match = line.subSequence(index, start).toString();
			matchList.add(match);
			index = start + separator.length();
			start = line.indexOf(separator, index);
		}

		matchList.add(line.subSequence(index, line.length()).toString());

		
		int resultSize = matchList.size();

		while (resultSize > 0 && matchList.get(resultSize - 1).equals(""))
			resultSize--;
		
		String[] result = new String[resultSize];
		return (String[]) matchList.subList(0, resultSize).toArray(result);
	}

}
