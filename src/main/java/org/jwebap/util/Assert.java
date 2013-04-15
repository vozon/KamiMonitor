package org.jwebap.util;

import java.util.Collection;
import java.util.Map;

/**
 * ǰ����������(����Common Template,www.commontemplate.com)
 * 
 * @author liangfei0201@163.com
 *
 */
public final class Assert {
	
	private Assert(){}
	
	public static void fail() {
		fail("���Դ˴����ᱻ���е�����ȴ������!");
	}
	
	public static void fail(String errorMessage) {
		throw new IllegalStateException(errorMessage);
	}
	
	public static void assertEquals(String value1, String value2) {
		assertEquals(value1, value2, "������ֵ���, ��" + value1 + "������" + value2 + "!");
	}
	
	public static void assertEquals(String value1, String value2, String errorMessage) {
		if (value1 == null || ! value1.equals(value2))
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertMatches(String value, String regex) {
		assertMatches(value, regex, "���Դ�ֵƥ����ʽ" + regex + ", ��" + value + "��ƥ��!");
	}
	
	public static void assertMatches(String value, String regex, String errorMessage) {
		if (! value.matches(regex))
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertAssignableFrom(Class targetClass, Class baseClass) {
		assertAssignableFrom(targetClass, baseClass, "����" + targetClass.getName() + "�̳���" + baseClass.getName() + ",��ȴ����!");
	}
	
	public static void assertAssignableFrom(Class targetClass, Class baseClass, String errorMessage) {
		if (! baseClass.isAssignableFrom(targetClass))
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertTrue(boolean value) {
		assertTrue(value, "���Դ�����Ӧ��Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertTrue(boolean value, String errorMessage) {
		if (! value)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertFalse(boolean value) {
		assertFalse(value, "���Դ�����Ӧ��Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertFalse(boolean value, String errorMessage) {
		if (value)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertNotNull(Object value) {
		assertNotNull(value, "���Դ˶���Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertNotNull(Object value, String errorMessage) {
		if (value == null)
			throw new NullPointerException(errorMessage);
	}
	
	public static void assertEmpty(Object value) {
		assertEmpty(value, "���Դ˶���Ϊ��, ��ȴ��Ϊ��!");
	}
	
	public static void assertEmpty(Object value, String errorMessage) {
		if (value != null)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertNotEmpty(Object value) {
		assertNotEmpty(value, "���Դ˶���Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertNotEmpty(Object value, String errorMessage) {
		if (value == null)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertNotEmpty(String value) {
		assertNotEmpty(value, "���Դ˶���Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertNotEmpty(String value, String errorMessage) {
		if (value == null || value.trim().length() == 0)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertNotEmpty(Collection value) {
		assertNotEmpty(value, "���Դ˶���Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertNotEmpty(Collection value, String errorMessage) {
		if (value == null || value.size() == 0)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertNotEmpty(Map value) {
		assertNotEmpty(value, "���Դ˶���Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertNotEmpty(Map value, String errorMessage) {
		if (value == null || value.size() == 0)
			throw new IllegalArgumentException(errorMessage);
	}
	
	public static void assertNotEmpty(Object[] value) {
		assertNotEmpty(value, "���Դ˶���Ϊ��, ��ȴΪ��!");
	}
	
	public static void assertNotEmpty(Object[] value, String errorMessage) {
		if (value == null || value.length == 0)
			throw new IllegalArgumentException(errorMessage);
	}
	
	public static void assertContain(String value, String sub) {
		assertContain(value, sub, "����\"" + value + "\"������\"" + sub + "\"�Ӵ�����ȴ������!");
	}
	
	public static void assertContain(String value, String sub, String errorMessage) {
		if (value == null || sub == null
				|| value.indexOf(sub) == -1) 
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertGreaterThan(int var, int value) {
		assertGreaterThan(var, value, "����" + var + "Ӧ����\"" + value + "\"!");
	}
	
	public static void assertGreaterThan(int var, int value, String errorMessage) {
		if (var <= value)
			throw new IllegalStateException(errorMessage);
	}
	
	public static void assertGreaterEqual(int var, int value) {
		assertGreaterEqual(var, value, "����" + var + "Ӧ���ڻ����\"" + value + "\"!");
	}
	
	public static void assertGreaterEqual(int var, int value, String errorMessage) {
		if (var < value)
			throw new IllegalStateException(errorMessage);
	}
}

