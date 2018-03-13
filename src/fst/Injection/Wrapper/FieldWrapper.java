package fst.Injection.Wrapper;

import java.util.UUID;

public class FieldWrapper {

    private UUID uuid = UUID.randomUUID();
    private String name;
    private boolean isStatic = false;

    public FieldWrapper(String name, boolean isStatic) {
        this.name = name;
        this.isStatic = isStatic;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return isStatic;
    }
}
