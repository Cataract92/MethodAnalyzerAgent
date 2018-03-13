package fst.Injection.Wrapper;

import fst.Visitors.MainClassVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ClassWrapper {

    static private HashMap<String, ClassWrapper> uuidClassMap = new HashMap<>();

    private UUID uuid = UUID.randomUUID();
    private String name;
    private MainClassVisitor mcv;

    private ArrayList<FieldWrapper> staticFields = new ArrayList<>();
    private ArrayList<FieldWrapper> nonStaticFields = new ArrayList<>();

    private ArrayList<MethodWrapper> staticMethods = new ArrayList<>();
    private ArrayList<MethodWrapper> nonStaticMethods = new ArrayList<>();

    public ClassWrapper (MainClassVisitor mcv,String name) {
        this.name = name;
        this.mcv = mcv;
        uuidClassMap.put(uuid.toString(),this);
    }

    public UUID getUuid() {
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

    public ArrayList<FieldWrapper> getNonStaticFields() {
        return nonStaticFields;
    }

    public ArrayList<MethodWrapper> getStaticMethods() {
        return staticMethods;
    }

    public ArrayList<MethodWrapper> getNonStaticMethods() {
        return nonStaticMethods;
    }

    public void addStaticField(FieldWrapper field)
    {
        staticFields.add(field);
    }

    public void addNonStaticField(FieldWrapper field)
    {
        nonStaticFields.add(field);
    }

    public void addStaticMethod(MethodWrapper method)
    {
        staticMethods.add(method);
    }

    public void addNonStaticMethod(MethodWrapper method)
    {
        nonStaticMethods.add(method);
    }

    public static ClassWrapper getClassWrapperByUUID(String uuid)
    {
        return uuidClassMap.get(uuid);
    }

    public static ArrayList<ClassWrapper> getAllClasses()
    {
        return new ArrayList<>(uuidClassMap.values());
    }
}
