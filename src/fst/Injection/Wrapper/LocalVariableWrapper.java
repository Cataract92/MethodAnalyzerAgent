package fst.Injection.Wrapper;

import java.util.UUID;

public class LocalVariableWrapper {

    private UUID uuid = UUID.randomUUID();
    private String name;
    private int index;

    public LocalVariableWrapper(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
