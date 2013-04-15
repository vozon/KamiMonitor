import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;



public class AsmAopClassAdapter extends ClassAdapter {

	private String enhancedSuperName, enhancedName;

	private String method;

	private String startInfo, endInfo;

	public AsmAopClassAdapter(ClassVisitor cv, String methodName, String start,
			String end) {
		// Responsechain ����һ�� ClassVisitor���������ǽ����� ClassWriter��
		// �����д���������
		super(cv);
		method = methodName;
		startInfo = start;
		endInfo = end;
	}

	// ��д visitMethod�����ʵ� "method" ����ʱ��
	// �����Զ��� MethodVisitor��ʵ�ʸ�д��������
	public MethodVisitor visitMethod(final int access, final String name,
			final String desc, final String signature, final String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		MethodVisitor wrappedMv = mv;
		if (mv != null) {
			if (name.equals(method)) {
				wrappedMv = new AsmAopMethodAdapter(mv, startInfo, endInfo);
			} else if (name.equals("<init>")) {
				wrappedMv = new ChangeToChildConstructorMethodAdapter(mv,
						enhancedSuperName);
			}
		}
		return wrappedMv;
	}

	public void visit(final int version, final int access, final String name,
			final String signature, final String superName,
			final String[] interfaces) {
		enhancedName = name.replace("/", "$") + "$EnhancedByASM"; // �ı�������
		enhancedSuperName = name; // �ı丸��
		super.visit(version, access, enhancedName, signature,
				enhancedSuperName, interfaces);
	}

	public String getEnhancedName() {
		return enhancedName;
	}
}