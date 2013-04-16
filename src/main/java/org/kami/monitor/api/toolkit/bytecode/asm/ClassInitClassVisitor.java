package org.kami.monitor.api.toolkit.bytecode.asm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ������class�ĳ�ʼ�����������������´���:
 * <p>
 * 	static <clinit>(){
 * 		<initializeName>();
 * 	}
 * <initializeName>:������ɵĳ�ʼ�����������ڳ�ʼ��inject class������һЩ��̬����
 * </p>
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date  Oct 14, 2007
 */
public class ClassInitClassVisitor extends ClassAdapter implements Constants, Opcodes {

    private static final Log log = LogFactory.getLog(ClassInitClassVisitor.class);
    private boolean             clinitVisited = false;
    private String              className;

    public ClassInitClassVisitor(final ClassVisitor cv) {
        super(cv);
    }


    public void visit(final int version, final int access, final String name, final String signature,
                      final String superName, final String[] interfaces) {

        clinitVisited = false;
        className     = name;

        super.visit(version, access, name, signature, superName, interfaces);
    }


    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     final String[] exceptions) {

        MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);

        if (classInitName.equals(name))
        {
            clinitVisited = true;
            visitor       = new ClassInitMethodVisitor(className, visitor);
        }

        return visitor;
    }


    public void visitEnd() {

        if (!clinitVisited)
        {
            MethodVisitor clinit = visitMethod(Modifier.PRIVATE_STATIC, classInitName, classInitDesc, null, null);

            clinit.visitCode();
            clinit.visitInsn(RETURN);
            clinit.visitEnd();
        }

        super.visitEnd();
    }
}
