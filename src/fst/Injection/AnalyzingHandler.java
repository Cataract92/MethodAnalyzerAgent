/*
 * Copyright (c) 2017.
 * Nico Feld
 * 1169233
 */

package fst.Injection;

import fst.Injection.Wrapper.MethodWrapper;

import java.util.HashMap;

public class AnalyzingHandler {

    public static void onMethodEnter(String uuid,Object reference)
    {
        MethodWrapper wrapper = MethodWrapper.getClassWrapperByUUID(uuid);
        wrapper.setReference(reference);
        wrapper.setRunning(true);
        wrapper.incrCallCount();
        if (wrapper.getName().equals("<init>"))
        {
            wrapper.getMmv().getMcv().getClassWrapper().addInstance(reference);
        }
    }

    public static void onMethodExit(String uuid)
    {
        MethodWrapper wrapper = MethodWrapper.getClassWrapperByUUID(uuid);
        wrapper.setReference(null);
        wrapper.setRunning(false);
    }
}
