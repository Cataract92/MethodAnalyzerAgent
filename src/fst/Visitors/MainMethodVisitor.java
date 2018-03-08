/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Visitors;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

public class MainMethodVisitor extends AdviceAdapter implements Opcodes
{

    private String methodName;

    private int localID;

    MainMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.methodName = name;
    }

/*
    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();

        if (methodName.equals("main"))
            mv.visitMethodInsn(INVOKESTATIC,"fst/Injection/AnalyzingHandler","injectShutdownHook","()V",false);

        localID = newLocal(Type.LONG_TYPE);

        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System","nanoTime","()J",false);
        mv.visitVarInsn(LSTORE, localID);
    }

    @Override
    protected void onMethodExit(int i) {
        super.onMethodExit(i);

        mv.visitLdcInsn(methodName);
        mv.visitVarInsn(LLOAD,localID);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System","nanoTime","()J",false);
        mv.visitMethodInsn(INVOKESTATIC,"fst/Injection/AnalyzingHandler","onMethodEnd","(Ljava/lang/String;JJ)V",false);

    }
    */
}