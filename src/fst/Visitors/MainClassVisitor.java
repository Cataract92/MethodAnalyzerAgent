/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Visitors;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.lang.instrument.Instrumentation;

public class MainClassVisitor extends ClassVisitor implements Opcodes
{
    private static final int API_VERSION = ASM5;

    private String className;
    private Instrumentation instrumentation;

    public MainClassVisitor(ClassVisitor cv, Instrumentation instrumentation, String className)
    {
        super(API_VERSION, cv);
        this.className = className;
        this.instrumentation = instrumentation;
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
        System.out.println(i+" "+s+" "+s1+" "+s2+" "+o);
        return super.visitField(i, s, s1, s2, o);
    }

    public String getClassName() {
        return className;
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
