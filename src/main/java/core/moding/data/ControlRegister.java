package core.moding.data;

import core.management.ControlEntry;
import core.management.DualRepository;
import game_logic.managers.controls.ControlContext;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.controls.CurrentControl;
import game_logic.repositories.controls.ConstantModControlRepository;
import game_logic.repositories.Identifier;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ControlRegister extends DualRepository.SingleRepository <ControlRegister.ControlVariable> {
    private final String modName;

    private final DualRepository.SingleRepository<ControlPart> partsList;
    private final DualRepository.SingleRepository <ControlVariable> variables;

    public ControlRegister(String modName) {
        variables = new DualRepository.SingleRepository<>();
        ConstantModControlRepository.variables.addEntry(modName, variables);
        partsList = new DualRepository.SingleRepository<>();
        ConstantModControlRepository.parts.addEntry(modName, partsList);
        this.modName = modName;
    }

    public void add (ControlVariable e) {
        if (e != null) {
            Identifier id = e.id();
            if (id.getSpace().equals(modName)) {
                variables.add(id, e);
                ControlModManager.controls.add (id, CurrentControl.fromControlVariable(e));
            }
        }
    }
    public void add (ControlPart part, Identifier id) {
        if (part != null && id != null) partsList.add(id, part);
    }

    public int countVariables () {
        return variables.count();
    }
    public int countParts () {
        return partsList.count();
    }

    public List<ControlPart> getParts () {
        List<ControlPart> p = new ArrayList<>(partsList.count());
        partsList.forEach((s, cp) -> p.add(cp));
        return p;
    }
    public List<ControlVariable> getVariables () {
        List<ControlVariable> v = new ArrayList<>(variables.count());
        variables.forEach((s, variable) -> v.add(variable));
        return v;
    }
    public String getNameFromID (Identifier identifier) {
        if (identifier == null)
            return "";
        ControlVariable var = variables.get(identifier);
        return var != null ? var.getName() : "";
    }

    public static class ControlVariable {
        private final ControlEntry controlEntry;
        private final String constantName;
        private final Identifier id;
        private final Consumer<ControlContext> actionOnLaunch;
        private final Identifier partIdentifier;

        public ControlVariable(MouseButton btn, String name, Identifier id, Consumer<ControlContext> actionOnLaunch, Identifier partIdentifier) {
            if (id != null)
                this.id = id;
            else throw new IllegalArgumentException("id cannot be null");
            controlEntry = new ControlEntry(btn);
            this.constantName = name != null ? name : "";
            this.actionOnLaunch = actionOnLaunch;
            this. partIdentifier = partIdentifier;
        }

        public ControlVariable(KeyCode kc, String name, Identifier id, Consumer<ControlContext> actionOnLaunch, Identifier partIdentifier) {
            if (id != null)
                this.id = id;
            else throw new IllegalArgumentException("id cannot be null");
            controlEntry = new ControlEntry(kc);
            this.constantName = name != null ? name : "";
            this.actionOnLaunch = actionOnLaunch;
            this. partIdentifier = partIdentifier;
        }
        public ControlEntry getControlEntry () {
            return controlEntry;
        }

        public String getName() {
            return constantName;
        }
        public Consumer<ControlContext> getActionOnLaunch () {
            return actionOnLaunch;
        }

        public Identifier id() {
            return id;
        }
        public Identifier getPartId () {
            return partIdentifier;
        }
    }
    public static class ControlPart {
        private final Identifier id;
        private final String constantName;

        public ControlPart (String name, Identifier id) {
            this.constantName = name != null ? name : "";
            this.id = id;
        }
        public ControlPart (Identifier id) {
            this(null, id);
        }
        public String getName () {
            return this.constantName;
        }
        public Identifier getIdentifier () {
            return id;
        }
    }
}
