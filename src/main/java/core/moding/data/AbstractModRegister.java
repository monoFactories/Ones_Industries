package core.moding.data;

public interface AbstractModRegister {
    ModGraphicRegister graphicRegister();
    ControlRegister controlRegister ();
    Object getOtherRegister (String id);
    void addNewOtherRegister (String id, Object objectRegister);
}
