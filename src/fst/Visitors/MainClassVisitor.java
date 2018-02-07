/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Visitors;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MainClassVisitor extends ClassVisitor implements Opcodes
{
    private static final int API_VERSION = ASM5;
    public MainClassVisitor(ClassVisitor cv) {
        super(API_VERSION, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String
            desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc,
                signature, exceptions);
        return new MainMethodVisitor(API_VERSION, mv, access, name,
                desc);
    }
}
