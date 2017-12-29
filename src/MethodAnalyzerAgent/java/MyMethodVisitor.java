import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

public class MyMethodVisitor extends AdviceAdapter implements Opcodes
{

    private long startTime;

    private String name;

    protected MyMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.name = name;
    }

    @Override
    protected void onMethodEnter() {
        mv.visitLdcInsn(new Long(22));
        mv.visitFieldInsn(PUTSTATIC,"SDRaytracer","time","J");

        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int i) {
        super.onMethodExit(i);
    }
}
