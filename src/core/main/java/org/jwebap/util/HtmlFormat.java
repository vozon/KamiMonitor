package org.jwebap.util;


/**
 * �ַ�����ʽ������
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  2008-2-4
 */
public class HtmlFormat {
	

	/**
	 * ���ı�ת�����ȷ��ʽ��Html
	 * ����:"<"ת����"&lt"�ȵ�
	 * @param html
	 * @return format����ı�
	 */
	public static String formatHtml(String html){
		String result=html;
		
		result=result.replaceAll("\"","&quot;");
		result=result.replaceAll("<","&lt;");
		result=result.replaceAll(">","&gt;");
		result=result.replaceAll(" ","&nbsp;");
		result=result.replaceAll("&","&amp;");
		result=result.replaceAll("\n","<br/>");
		return result;
	}
	
	
}
