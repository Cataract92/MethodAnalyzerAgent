import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

class ProfilingClassVisitor extends ClassVisitor implements Opcodes
{
    private static final int API_VERSION = ASM5;
    ProfilingClassVisitor(ClassVisitor cv) {
        super(API_VERSION, cv);
    }

    @Override
    public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
        cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,"SDRaytracer","time","J",0);
        return super.visitField(i, s, s1, s2, o);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String
            desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc,
                signature, exceptions);
        return new MyMethodVisitor(API_VERSION, mv, access, name,
                desc);
    }
}
