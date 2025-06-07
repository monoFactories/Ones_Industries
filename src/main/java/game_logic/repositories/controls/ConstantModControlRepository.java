package game_logic.repositories.controls;

import core.management.DualRepository;
import core.moding.data.ControlRegister;

public class ConstantModControlRepository {
    public static final DualRepository<ControlRegister.ControlVariable> variables = new DualRepository<>();
    public static final DualRepository<ControlRegister.ControlPart> parts = new DualRepository<>();
}
