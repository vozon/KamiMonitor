package org.kami.monitor.api.toolkit.bytecode.asm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ���ʼ�������ֽ��������
 * 
 * �������ɳ�ʼ�������ֽ��룬��ʼ��ע��ľ�̬��Ա
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  Oct 14, 2007
 * @see ClassInitClassVisitor
 * @see ProxyMethodVisitor
 */
public class ClassInitMethodVisitor extends MethodAdapter implements Constants {

    private static final Log log = LogFactory.getLog(ClassInitMethodVisitor.class);
    private String              className;

    public ClassInitMethodVisitor(String className, MethodVisitor mv) {

        super(mv);

        this.className = className;
    }


    public void visitCode() {
        super.visitMethodInsn(Opcodes.INVOKESTATIC, className, initializeName, "()V");
        super.visitCode();
    }
}
