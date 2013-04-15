package org.jwebap.plugin.tracer.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;

import javax.sql.DataSource;

import org.jwebap.asm.Opcodes;
import org.jwebap.core.TraceLiftcycleManager;
import org.jwebap.toolkit.bytecode.asm.MethodInjectHandler;

/**
 * ���ݿ���ע��handle
 * 
 * @author leadyu
 * @since Jwebap 0.5
 * @date 2007-8-14
 */
public class JdbcDriverInjectHandle implements MethodInjectHandler {

	/**
	 * ���й켣����
	 */
	private transient TraceLiftcycleManager _container = null;

	private ConnectionEventListener[] _listeners = null;

	public JdbcDriverInjectHandle(TraceLiftcycleManager container,
			ConnectionEventListener[] listeners) {
		_container = container;
		_listeners = listeners;
	}

	public Object invoke(Object target, Method method, Method methodProxy,
			Object[] args) throws Throwable {
		Object o;
		try {
			o = methodProxy.invoke(target, args);
		} catch (InvocationTargetException e) {
			// �׳�ԭ���쳣
			throw e.getCause();
		} finally {

		}
		if (!Modifier.isPrivate(method.getModifiers())
				&& o instanceof Connection
				&& !(o instanceof ProxyConnection)) {
			return new ProxyConnection(_container, (Connection) o,
					_listeners);
		} else if (!Modifier.isPrivate(method.getModifiers())
				&& o instanceof DataSource
				&& !(o instanceof ProxyDataSource)) {
			return new ProxyDataSource(_container, (DataSource) o,
					_listeners);
		} else {
			return o;
		}

	}

}
