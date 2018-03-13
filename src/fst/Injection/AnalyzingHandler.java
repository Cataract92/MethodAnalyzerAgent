/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Injection;

import fst.Injection.Wrapper.MethodWrapper;

import java.text.DecimalFormat;
import java.util.HashMap;

public class AnalyzingHandler {

    /*

        To prevent a heapOverflow respectively the deletion of objects by the garbage collector
        the processingTimes are not saved in a List and get calculated in the end, but get calculated on the fly.
        Unfortunately this causes rounding errors and an imprecise result.

     */

    private static class TimeCounter{
        private long callCounter = 0L;
        private double processingTime = 0d;

        void update(long processingTime)
        {
            this.processingTime = (this.processingTime / (callCounter + 1)) * callCounter + (processingTime / (callCounter + 1d));
            callCounter++;
        }

        long getCallCounter() {
            return callCounter;
        }

        double getProcessingTime() {
            return processingTime;
        }
    }

    public static String targetPackageName;

    private static HashMap<String,TimeCounter> methodCallTimes = new HashMap<>();

    public static void injectShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {

            DecimalFormat decimalFormat = new DecimalFormat("#.#######");

            System.out.println("\n\nProfiling of class: " + targetPackageName + "\n");
            for (String methodName : methodCallTimes.keySet())
            {
                double processingTime = methodCallTimes.get(methodName).getProcessingTime();
                long callCounter = methodCallTimes.get(methodName).getCallCounter();

                System.out.println("MethodName: "+methodName);
                System.out.println("Times Called: "+callCounter);

                System.out.println("Average Processing Time " + decimalFormat.format(processingTime / 1000000000d) +"s\n");
            }
        }, "Shutdown-thread"));
    }

    public static void onMethodEnter(String uuid,Object reference)
    {
        System.out.println(MethodWrapper.getClassWrapperByUUID(uuid).getName()+" "+reference);
        MethodWrapper.getClassWrapperByUUID(uuid).setRunning(true);
    }

    public static void onMethodExit(String uuid)
    {
        MethodWrapper.getClassWrapperByUUID(uuid).setRunning(false);
    }
}
