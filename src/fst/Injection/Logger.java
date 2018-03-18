/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Injection;

import fst.Injection.Wrapper.ClassWrapper;
import fst.Injection.Wrapper.FieldWrapper;
import fst.Injection.Wrapper.MethodWrapper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Logger extends Thread {

    private class Entry
    {
        long timeStamp;

        public Entry(int timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

    private HashMap<String, ArrayList<Entry>> nameEntriesMap = new HashMap<>();

    private Instrumentation inst;
    private ArrayList<Integer> timeStamps = new ArrayList<>();

    private PrintWriter writer = null;

    public Logger(Instrumentation inst) {
        this.inst = inst;

        try {
            writer = new PrintWriter("output.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            writer.close();
            this.interrupt();
        }));

    }

    @Override
    public void run() {
        super.run();

        long startTime = System.currentTimeMillis();

        while (true)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long countObject = 0;
            long memoryuse = 0;
            long methodCalls = 0;

            for (ClassWrapper cw : (ArrayList<ClassWrapper>) ClassWrapper.getAllClasses().clone())
            {
                for (FieldWrapper fw : (ArrayList<FieldWrapper>) cw.getStaticFields().clone()){
                    try {
                        Field f = Class.forName(cw.getName()).getDeclaredField(fw.getName());
                        f.setAccessible(true);

                        if (f.get(null) != null)
                            memoryuse += inst.getObjectSize(f.get(null));

                    } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                for (Object obj : (ArrayList<Object>) cw.getInstances().clone())
                {
                    if (obj != null) {
                        memoryuse += inst.getObjectSize(obj);
                        countObject++;
                    }
                }

                for (MethodWrapper method : cw.getMethods())
                {
                    methodCalls += method.getCallCount();
                    if (method.isRunning())
                        memoryuse += method.getMaxStack();
                }

            }

            writer.printf("%d;%d;%d;%d\n",(System.currentTimeMillis() - startTime),countObject,memoryuse,methodCalls);
        }
    }
}
