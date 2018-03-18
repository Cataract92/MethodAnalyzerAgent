package fst.Injection.Wrapper;

import fst.Visitors.MainMethodVisitor;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MethodWrapper {

    private static HashMap<String,MethodWrapper> uuidMethodMap = new HashMap<>();
    private static ArrayList<MethodWrapper> runningMethods = new ArrayList<>();

    private String uuid = UUID.randomUUID().toString();
    private String name;
    private boolean isStatic;
    private Object reference = null;
    private long callCount = 0;
    private int maxStack;
    private MainMethodVisitor mmv;

    public MethodWrapper(MainMethodVisitor mmv,String name, boolean isStatic) {
        this.mmv = mmv;
        this.name = name;
        this.isStatic = isStatic;
        uuidMethodMap.put(uuid,this);
    }

    public MainMethodVisitor getMmv() {
        return mmv;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isRunning()
    {
        return runningMethods.contains(this);
    }

    public void setRunning(boolean isRunning)
    {
        if (isRunning && !runningMethods.contains(this))
            runningMethods.add(this);

        if (!isRunning && runningMethods.contains(this))
            runningMethods.remove(this);
    }

    public String getUuid() {
        return uuid;
    }

    public static MethodWrapper getClassWrapperByUUID(String uuid)
    {
        return uuidMethodMap.get(uuid);
    }

    public static ArrayList<MethodWrapper> getRunningMethods() {
        return runningMethods;
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public void incrCallCount()
    {
        callCount++;
    }

    public long getCallCount() {
        return callCount;
    }
}
