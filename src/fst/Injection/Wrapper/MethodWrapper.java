package fst.Injection.Wrapper;

import fst.Visitors.MainMethodVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MethodWrapper {

    private static HashMap<String,MethodWrapper> uuidMethodMap = new HashMap<>();

    private UUID uuid = UUID.randomUUID();
    private String name;
    private boolean isStatic;
    private boolean isRunning = false;
    private ArrayList<LocalVariableWrapper> localVariables = new ArrayList<>();
    private MainMethodVisitor mmv;

    public MethodWrapper(MainMethodVisitor mmv,String name, boolean isStatic) {
        this.mmv = mmv;
        this.name = name;
        this.isStatic = isStatic;
        uuidMethodMap.put(uuid.toString(),this);
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

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public ArrayList<LocalVariableWrapper> getLocalVariables() {
        return localVariables;
    }

    public void addLocalVariable(LocalVariableWrapper variable) {
        this.localVariables.add(variable);
    }

    public UUID getUuid() {
        return uuid;
    }

    public static MethodWrapper getClassWrapperByUUID(String uuid)
    {
        return uuidMethodMap.get(uuid);
    }
}
