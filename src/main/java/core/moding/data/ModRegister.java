package core.moding.data;

import core.moding.mod.Mod;

import java.util.concurrent.ConcurrentHashMap;

public class ModRegister implements AbstractModRegister {
    public final ModGraphicRegister graphics;
    public final ControlRegister controls;
    private final ConcurrentHashMap<String, Object> otherRegistries;
    public ModRegister(Mod mod) {
        this.graphics = new ModGraphicRegister(mod.getParameter().getName());
        this.controls = new ControlRegister(mod.getParameter().getName());
        otherRegistries = new ConcurrentHashMap<>();
    }
    public ModGraphicRegister graphicRegister() {
        return graphics;
    }
    public ControlRegister controlRegister () {
        return controls;
    }
    public Object getOtherRegister(String id) {
        return otherRegistries.get(id);
    }

    @Override
    public void addNewOtherRegister(String id, Object objectRegister) {
        if (id != null) {
            if (objectRegister == null)
                otherRegistries.remove(id);
            else
                otherRegistries.put (id, objectRegister);
        }
    }

}
