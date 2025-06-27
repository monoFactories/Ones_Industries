package core.moding.data;

import core.management.ControlEntry;
import core.management.DualRepository;
import game_logic.managers.controls.ControlContext;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.controls.ControlsExecutor;
import game_logic.managers.controls.CurrentControl;
import game_logic.repositories.controls.ConstantModControlRepository;
import game_logic.repositories.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ControlRegister {
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
                Consumer<ControlContext> action = e.actionOnLaunch;
                if (action != null) {
                    ControlsExecutor.executor.add(id, action);
                }
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
    public List<ControlVariable> getVariablesAsList () {
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
    public ControlVariable createVariable (ControlEntry ce, String name, Identifier id, Consumer<ControlContext> actionOnLaunch, Identifier partIdentifier) {
        return new ControlVariable(ce, name, new Identifier(modName, id.getName()), actionOnLaunch, partIdentifier);
    }
    public ControlPart createPart (String name, Identifier id) {
        return new ControlPart(name, new Identifier(modName, id.getName()));
    }

    public DualRepository.SingleRepository<ControlPart> getPartsList() {
        return partsList;
    }
    public DualRepository.SingleRepository<ControlVariable> getVariables() {
        return variables;
    }
    public String getModName() {
        return modName;
    }

    @Override
    public String toString() {
        return "ControlRegister {" +
                "modName = \"" + modName + "\"\n" +
                "partsList = " + partsList + ",\n" +
                "variables=" + variables +
                '}';
    }

    public static class ControlVariable {
        private final ControlEntry controlEntry;
        private final String constantName;
        private final Identifier id;
        private final Consumer<ControlContext> actionOnLaunch;
        private final Identifier partIdentifier;

        private ControlVariable (ControlEntry ce, String name, Identifier id, Consumer<ControlContext> actionOnLaunch, Identifier partIdentifier) {
            if (id == null) throw new IllegalArgumentException("id cannot be null");
            controlEntry = ce == null ? ControlEntry.EMPTY : ce;
            this.constantName = name != null ? name : "";
            this.id = id;
            this.actionOnLaunch = actionOnLaunch;
            this.partIdentifier = partIdentifier;
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

        private ControlPart (String name, Identifier id) {
            if (id == null) throw new IllegalArgumentException("id can't be null");
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
