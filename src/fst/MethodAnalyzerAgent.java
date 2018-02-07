package fst;
/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

/*
    Usage:
        Add as a VM parameter: -javaagent:{PathToJar}={TargetClassName}
        eg.: -javaagent:/home/fst/MethodAnalyzerAgent.jar=SDRaytracer

 */

import fst.Injection.AnalyzingHandler;
import fst.Visitors.MainClassVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.lang.instrument.Instrumentation;

public class MethodAnalyzerAgent {

    public static void premain(String agentArgs,
                               Instrumentation inst) {

        AnalyzingHandler.targetClassName = agentArgs;

        if (agentArgs == null)
            AnalyzingHandler.targetClassName = "SDRaytracer";

        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) ->
        {
            if (AnalyzingHandler.targetClassName.equals(className)) {
                try {
                    ClassReader classReader = new ClassReader(classfileBuffer);
                    ClassWriter classWriter = new ClassWriter(classReader,
                            ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                    ClassVisitor mainClassVisitor = new
                            MainClassVisitor(classWriter);
                    classReader.accept(mainClassVisitor,
                            ClassReader.EXPAND_FRAMES);
                    return classWriter.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                    return classfileBuffer;
                } }

            return classfileBuffer;
        });
    }
}
