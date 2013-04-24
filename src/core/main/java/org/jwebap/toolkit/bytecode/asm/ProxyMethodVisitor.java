package org.jwebap.toolkit.bytecode.asm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.asm.Label;
import org.jwebap.asm.MethodVisitor;
import org.jwebap.asm.Opcodes;
import org.jwebap.asm.Type;
import org.jwebap.asm.commons.GeneratorAdapter;
import org.jwebap.asm.commons.Method;

/**
 * ���ɴ���������ԭ����˽�л��������·������£�
 * <p>
 * <access> <methodName>(){
 * 
 * if(handle==null){ return <proxyMethodName>(); }else{ return
 * handle.invoke(this,method,proxyMethod,args[]); } }
 * 
 * private <proxyMethodName>(){ //ԭ�������� }
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date Oct 20, 2007
 */
public class ProxyMethodVisitor extends GeneratorAdapter implements Constants,
		Opcodes {

	private static final Log log = LogFactory.getLog(ProxyMethodVisitor.class);

	private boolean isStatic;

	private boolean isVoidReturn;

	private Type classType;

	private String handlerFieldName;

	private String methodFieldName;

	private String methodProxyFieldName;

	private String targetMethodName;

	private Method method;

	public ProxyMethodVisitor(int access, Method method, MethodVisitor mv,
			Type classType, String targetMethodName, String handlerFieldName,
			String methodFieldName, String methodProxyFieldName) {

		super(access, method, mv);

		this.method = method;
		this.isStatic = Modifier.isStatic(access);
		this.isVoidReturn = Type.VOID_TYPE.equals(method.getReturnType());
		this.classType = classType;
		this.targetMethodName = targetMethodName;
		this.handlerFieldName = handlerFieldName;
		this.methodFieldName = methodFieldName;
		this.methodProxyFieldName = methodProxyFieldName;
	}

	/**
	 * �������ɵĴ������ڣ�����ԭ����ʱ�����˷������handle�ķ�ʽ��������Ҫ�Է���ֵ���д���
	 * 
	 * ��ԭ��������ֵΪ������ʱ,handleͨ��������÷��ص���Object����Ҫ��Ӧ����
	 * 
	 * �μ�MethodInjectHandler
	 * 
	 * @param type
	 * @see MethodInjectHandler
	 * @deprecated ��Ϊͨ������isVoidReturn������������
	 */
	private void unbox_or_zero(Type type) {
		if (isPrimitive(type)) {
			if (type != Type.VOID_TYPE) {
				Label nonNull = newLabel();
				Label end = newLabel();
				dup();
				//���ԭ�������ص��Ǽ�����,��handle���ص���Null����ôת��Ϊ0L����
				ifNonNull(nonNull);
				pop();
				zero_or_null(type);//ת��Ϊ0L
				goTo(end);
				mark(nonNull);
				unbox(type);
				mark(end);
			}
		} else {
			checkCast(type);
		}
	}

	//-------------------------------
	//nullת����0
	//-------------------------------
	private void zero_or_null(Type type) {
		if (isPrimitive(type)) {
			switch (type.getSort()) {
			case Type.DOUBLE:
				push(0d);
				break;
			case Type.LONG:
				push(0L);
				break;
			case Type.FLOAT:
				push(0f);
				break;
			case Type.VOID:
				mv.visitInsn(Opcodes.ACONST_NULL);
			default:
				push(0);
			}
		} else {
			mv.visitInsn(Opcodes.ACONST_NULL);
		}
	}

	private boolean isPrimitive(Type type) {
		switch (type.getSort()) {
		case Type.ARRAY:
		case Type.OBJECT:
			return false;
		default:
			return true;
		}
	}

	public void visitCode() {

		Label gotoLabel = newLabel();
		Type rt = method.getReturnType();
		int result = newLocal(rt);
		// -------------------------------------------------------------------------------

		getStatic(classType, handlerFieldName, MethodHandler.TYPE);
		dup();
		ifNull(gotoLabel);

		pushThis();
		getStatic(classType, methodFieldName, Type
				.getType(java.lang.reflect.Method.class));
		getStatic(classType, methodProxyFieldName, Type
				.getType(java.lang.reflect.Method.class));
		loadArgArray();
		invokeInterface(MethodHandler.TYPE, MethodHandler.invoke);

		if (!isVoidReturn) {
			// �����Ƿ�����ã����Զ���handle���ص�Object��Ҫ�����жϼ����������null���Լ�����ת��������
			unbox(rt);
			storeLocal(result);
			loadLocal(result);
		}

		returnValue();

		mark(gotoLabel);// �ж�handle�Ƿ�Ϊnull�����Ϊnull����ԭ����

		if (isStatic) {
			loadArgs(); // ���ز���
			invokeStatic(classType, new Method(targetMethodName, method
					.getDescriptor()));
		} else {
			loadThis(); // ���ص���ʵ��
			loadArgs(); // ���ز���
			invokeVirtual(classType, new Method(targetMethodName, method
					.getDescriptor()));
		}

		if (!isVoidReturn) {
			storeLocal(result);
			loadLocal(result);

		}
		returnValue();

		// -------------------------------------------------------------------------------
		endMethod();
	}

	private void pushThis() {

		if (isStatic) {
			push("test");
		} else {
			loadThis();
		}
	}
}
