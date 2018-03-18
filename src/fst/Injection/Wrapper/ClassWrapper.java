package fst.Injection.Wrapper;

import fst.Visitors.MainClassVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ClassWrapper {

    static private HashMap<String, ClassWrapper> uuidClassMap = new HashMap<>();

    private String uuid = UUID.randomUUID().toString();
    private String name;
    private MainClassVisitor mcv;

    private ArrayList<Object> instances = new ArrayList<>();

    private ArrayList<FieldWrapper> staticFields = new ArrayList<>();

    private ArrayList<MethodWrapper> methods = new ArrayList<>();

    public ClassWrapper(MainClassVisitor mcv,String name) {
        this.name = name;
        this.mcv = mcv;
        uuidClassMap.put(uuid,this);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public MainClassVisitor getMcv() {
        return mcv;
    }

    public ArrayList<FieldWrapper> getStaticFields() {
        return staticFields;
    }

    public ArrayList<MethodWrapper> getMethods() {
        return methods;
    }

    public void addStaticField(FieldWrapper field)
    {
        staticFields.add(field);
    }

    public void addMethod(MethodWrapper method)
    {
        methods.add(method);
    }

    public static ClassWrapper getClassWrapperByUUID(String uuid)
    {
        return uuidClassMap.get(uuid);
    }

    public static ArrayList<ClassWrapper> getAllClasses()
    {
        return new ArrayList<>(uuidClassMap.values());
    }

    public void addInstance(Object obj)
    {
        instances.add(obj);
    }

    public ArrayList<Object> getInstances() {
        return instances;
    }
}
