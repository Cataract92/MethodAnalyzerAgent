/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst;

/*
    Usage:
        Add as a VM parameter: -javaagent:{PathToJar}={TargetClasses}
        eg.: -javaagent:/home/fst/MethodAnalyzerAgent.jar=PathfinderTester,AStar
*/

import fst.Injection.Logger;
import fst.Visitors.MainClassVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import java.lang.instrument.Instrumentation;
import java.util.*;

public class MethodAnalyzerAgent {

    public static void premain(String agentArgs,
                               Instrumentation inst) {

        ArrayList<String> classes = new ArrayList<>();

        if (agentArgs != null) {

            StringTokenizer tokenizer = new StringTokenizer(agentArgs, ",");

            while (tokenizer.hasMoreTokens()) {
                classes.add(tokenizer.nextToken());
            }
        } else {
            classes.add("PathfinderTester");
            classes.add("AStar");
            classes.add("MinMax");
            classes.add("PFAlgorithm");
            classes.add("VisibilityGraphs");
            classes.add("Level1");
            classes.add("CustomMouseListener");
        }

        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) ->
        {
            if (classes.stream().anyMatch(className::contains)) {
                System.out.println(className);
                try {
                    ClassReader classReader = new ClassReader(classfileBuffer);
                    ClassWriter classWriter = new ClassWriter(classReader,
                            ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                    ClassVisitor mainClassVisitor = new
                            MainClassVisitor(classWriter,inst,className);
                    classReader.accept(mainClassVisitor,
                            ClassReader.EXPAND_FRAMES);
                    return classWriter.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }

            return classfileBuffer;
        });

        Logger l = new Logger(inst);
        l.start();
    }

}