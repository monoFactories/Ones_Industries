package game_logic.managers.controls;

import core.management.DualRepository;
import core.moding.data.ControlRegister;
import game_logic.repositories.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentModsController extends DualRepository<CurrentControl> {
    /// storage
    public transient final ConcurrentHashMap<CurrentControl, ControlsActionStorage> binds = new ConcurrentHashMap<>();

    @Override
    public void add(Identifier identifier, CurrentControl currentControl) {
        super.add(identifier, currentControl);
        rebinds();
    }

    public Identifier getActionIDFromBinds(CurrentControl current) {
        if (current != null) {
            ControlsActionStorage cas = binds.get (current);
            if (cas != null) {
                return cas.handleClick();
            }
        }
        return null;
    }

    public void rebinds() {
        binds.clear();
        HashMap <CurrentControl, List <Identifier>> preComplete = new HashMap<>();
        forEach((modName, modControls) -> modControls.forEach((id, control) -> {
            List<Identifier> idsForThisControl = preComplete.computeIfAbsent(control, k -> new ArrayList<>());
            idsForThisControl.add(new Identifier(modName, id));
        }));
        preComplete.forEach((cc, identifiers) -> binds.put(cc, new ControlsActionStorage(cc, identifiers)));
    }

    /// used when downloading settings from a file
    public void append(CurrentModsController appended) {
        if (appended != null && this != appended) {
            appended.forEach ((modName, single) -> single.forEach((id, currentControl) -> {
                Identifier path = new Identifier(modName, id);
                if (get(path) != null) {
                    add(path, currentControl);
                }
            }));
        }
    }
    public static SingleRepository<CurrentControl> fromConstant (ControlRegister constantRepository) {
        SingleRepository<CurrentControl> single = new SingleRepository<>();
        constantRepository.getVariables().forEach(controlVariable -> single.add (controlVariable.id(), CurrentControl.fromControlVariable(controlVariable)));
        return single;
    }
}
