package mod.base.modification.repositories;

import core.management.DualRepository;

import java.util.concurrent.ConcurrentHashMap;

public class Registration {
    public static final Registration register = new Registration();
    private final ConcurrentHashMap<String, DualRepository<?>> values;

    public Registration() {
        values = new ConcurrentHashMap<>();
    }
    public DualRepository<?> get (String id) {
        return values.get(id);
    }
    public void put (String id, DualRepository<?> object) {
        if (id != null && object != null) {
            values.putIfAbsent(id, object);
        }
    }
    public void remove (String id) {
        if (id != null) {
            values.remove(id);
        }
    }
}
