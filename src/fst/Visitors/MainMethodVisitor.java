/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Visitors;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

public class MainMethodVisitor extends AdviceAdapter implements Opcodes
{

    private String methodName;
    private MainClassVisitor mcv;

    private int localID;

    MainMethodVisitor(int api, MethodVisitor mv, MainClassVisitor mcv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        this.methodName = name;
        this.mcv = mcv;
    }

    @Override
    public void visitFieldInsn(int var1, String var2, String var3, String var4) {
        super.visitFieldInsn(var1, var2, var3, var4);
    }

    @Override
    public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
        super.visitLocalVariable(var1, var2, var3, var4, var5, var6);
    }

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

    public String getMethodName() {
        return methodName;
    }

    public MainClassVisitor getMcv() {
        return mcv;
    }

    public int getLocalID() {
        return localID;
    }
}