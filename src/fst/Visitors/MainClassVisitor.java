/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Visitors;

import fst.Injection.Wrapper.ClassWrapper;
import fst.Injection.Wrapper.FieldWrapper;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.lang.instrument.Instrumentation;

public class MainClassVisitor extends ClassVisitor implements Opcodes
{
    private static final int API_VERSION = ASM5;

    private Instrumentation instrumentation;
    private ClassWrapper classWrapper;


    public MainClassVisitor(ClassVisitor cv, Instrumentation instrumentation, String className)
    {
        super(API_VERSION, cv);
        this.instrumentation = instrumentation;
        this.classWrapper = new ClassWrapper(this,className);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String
            desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc,
                signature, exceptions);
        return new MainMethodVisitor(API_VERSION, mv, this, access, name,
                desc);
    }

    @Override
    public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
        if ((i & Opcodes.ACC_STATIC) != 0)
            classWrapper.addStaticField(new FieldWrapper(s));

        return super.visitField(i, s, s1, s2, o);
    }

    public ClassWrapper getClassWrapper() {
        return classWrapper;
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
