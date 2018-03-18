package fst.Injection.Wrapper;

import java.util.UUID;

public class FieldWrapper {

    private String uuid = UUID.randomUUID().toString();
    private String name;

    public FieldWrapper(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

}
