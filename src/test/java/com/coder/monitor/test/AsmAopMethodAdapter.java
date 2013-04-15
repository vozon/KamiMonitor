package com.coder.monitor.test;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmAopMethodAdapter extends MethodAdapter implements Opcodes {

	// private final static int EXCEPTION_STACK = 2 +
	// 1;//max_stack������Ҫ�ܹ�����2��������ַ(��ط���ʹ��)��1��exception��ַ

	private Label try_catch_start, try_catch_end;

	private String startInfo, endInfo;

	public AsmAopMethodAdapter(MethodVisitor mv, String start, String end) {
		super(mv);
		try_catch_start = new Label();
		try_catch_end = new Label();
		startInfo = start;
		endInfo = end;
	}

	public void visitCode() {
		mv.visitCode();
		mv.visitLabel(try_catch_start);

		mv.visitLdcInsn(startInfo);
		// AsmAopInvoker������д���·�����磺com.asm.AsmAopInvoker Ӧд��
		// com/asm/AsmAopInvoker
		mv.visitMethodInsn(INVOKESTATIC, "AsmAopInvoker", "methodStart",
				"(Ljava/lang/String;)V");
	}

	public void visitInsn(int opcode) {
		if (opcode >= IRETURN && opcode <= RETURN) {
			mv.visitLdcInsn(endInfo);
			// AsmAopInvoker������д���·�����磺com.asm.AsmAopInvoker Ӧд��
			// com/asm/AsmAopInvoker
			mv.visitMethodInsn(INVOKESTATIC, "AsmAopInvoker", "methodEnd",
					"(Ljava/lang/String;)V");
		}
		mv.visitInsn(opcode);
	}

	public void visitEnd() {
		mv.visitLabel(try_catch_end);
		mv.visitTryCatchBlock(try_catch_start, try_catch_end, try_catch_end,
				null);
		mv.visitLdcInsn(endInfo);
		mv.visitMethodInsn(INVOKESTATIC, "AsmAopInvoker", "methodEnd",
				"(Ljava/lang/String;)V");
		mv.visitInsn(Opcodes.ATHROW);
		mv.visitEnd();
	}

	// public void visitMaxs(int maxStack,int maxLocals){
	// ��֤max stack�㹻��
	// mv.visitMaxs(Math.max(EXCEPTION_STACK,maxStack), maxLocals);
	// }
}