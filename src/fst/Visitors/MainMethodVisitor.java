/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Visitors;

import fst.Injection.Wrapper.LocalVariableWrapper;
import fst.Injection.Wrapper.MethodWrapper;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

import java.util.UUID;

public class MainMethodVisitor extends AdviceAdapter implements Opcodes {

    private String methodName;
    private MainClassVisitor mcv;
    private MethodWrapper methodWrapper;
    private String uuid = (UUID.randomUUID()).toString();

    private int localID;

    MainMethodVisitor(int api, MethodVisitor mv, MainClassVisitor mcv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.methodName = name;
        this.mcv = mcv;
        if ((access & Opcodes.ACC_STATIC) != 0) {
            methodWrapper = new MethodWrapper(this, name, true);
            mcv.getClassWrapper().addStaticMethod(methodWrapper);
        } else {
            methodWrapper = new MethodWrapper(this, name, false);
            mcv.getClassWrapper().addNonStaticMethod(methodWrapper);
        }
    }

    @Override
    public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
        methodWrapper.addLocalVariable(new LocalVariableWrapper(var1, var6));
        super.visitLocalVariable(var1, var2, var3, var4, var5, var6);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();

        if (methodName.equals("main"))
            mv.visitMethodInsn(INVOKESTATIC, "fst/Injection/AnalyzingHandler", "injectShutdownHook", "()V", false);

        mv.visitLdcInsn(methodWrapper.getUuid().toString());

        if (!methodWrapper.isStatic())
            mv.visitVarInsn(Opcodes.ALOAD, 0); // Push "this"
        else
            mv.visitInsn(Opcodes.ACONST_NULL);

        mv.visitMethodInsn(INVOKESTATIC, "fst/Injection/AnalyzingHandler", "onMethodEnter", "(Ljava/lang/String;Ljava/lang/Object;)V", false);

    }


    @Override
    protected void onMethodExit(int i) {
        super.onMethodExit(i);

        mv.visitLdcInsn(methodWrapper.getUuid().toString());
        mv.visitMethodInsn(INVOKESTATIC, "fst/Injection/AnalyzingHandler", "onMethodExit", "(Ljava/lang/String;)V", false);

    }

    public String getMethodName() {
        return methodName;
    }

    public MethodWrapper getMethodWrapper() {
        return methodWrapper;
    }

    public MainClassVisitor getMcv() {
        return mcv;
    }

    public int getLocalID() {
        return localID;
    }

    public String getUuid() {
        return uuid;
    }
}