import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;



public class ChangeToChildConstructorMethodAdapter extends MethodAdapter { 
	 
	private String superClassName; 

	 public ChangeToChildConstructorMethodAdapter(MethodVisitor mv, 
		 String superClassName) { 
		 super(mv); 
		 this.superClassName = superClassName; 
	 } 

	 public void visitMethodInsn(int opcode, String owner, String name, 
		 String desc) { 
		 // ���ø���Ĺ��캯��ʱ
		 if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) { 
			 owner = superClassName; 
		 } 
		 super.visitMethodInsn(opcode, owner, name, desc);// ��д����Ϊ superClassName 
	 } 
} 