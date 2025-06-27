package game_logic.managers.controls;

import com.google.gson.*;
import core.management.DualRepository;
import game_logic.repositories.Identifier;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentModsController extends DualRepository<CurrentControl> {
    public static final CurrentModsControlsJson CURRENT_MODS_CONTROLS_JSON_ADAPTER = new CurrentModsControlsJson();
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
    //public static SingleRepository<CurrentControl> fromConstant (ControlRegister constantRepository) {
    //    SingleRepository<CurrentControl> single = new SingleRepository<>();
    //    constantRepository.getVariables().forEach(controlVariable -> single.add (controlVariable.id(), CurrentControl.fromControlVariable(controlVariable)));
    //    return single;
    //}
    public static final class CurrentModsControlsJson implements JsonSerializer<CurrentModsController>, JsonDeserializer<CurrentModsController> {
        public static final String MOUSE_FLAG_NAME = "isMouse", CONTROL_VALUE_NAME = "value";
        @Override
        public JsonElement serialize(CurrentModsController controller, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject mods = new JsonObject();
            controller.forEach((modName, endRepo) -> {
                JsonObject mod = new JsonObject();
                endRepo.forEach((endName, currentControl) -> {
                    JsonObject controlElement = new JsonObject();
                    boolean isMouse = currentControl.isMouse();
                    controlElement.addProperty(MOUSE_FLAG_NAME, isMouse);
                    String property;
                    if (isMouse) {
                        //controlElement.addProperty(CONTROL_VALUE_NAME, currentControl.getBtn().name());
                        MouseButton btn = currentControl.getBtn();
                        if (btn == null)
                            property = "";
                        else
                            property = btn.name();
                    }
                    else {
                        KeyCode keyCode = currentControl.getKey();
                        if (keyCode == null)
                            property = "";
                        else
                            property = keyCode.name();
                    }
                    controlElement.addProperty(CONTROL_VALUE_NAME, property);
                    mod.add (endName, controlElement);
                });
                mods.add (modName, mod);
            });
            return mods;
        }
        @Override
        public CurrentModsController deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                CurrentModsController controller = new CurrentModsController();
                JsonObject map = jsonElement.getAsJsonObject();
                map.asMap().forEach((mod, modContent) -> {
                    if (modContent.isJsonObject()) {
                        SingleRepository<CurrentControl> inner = new SingleRepository<>();
                        JsonObject modContentObject = modContent.getAsJsonObject();
                        modContentObject.asMap().forEach((endControl, serializedControl) -> {
                            if (serializedControl.isJsonObject()) {
                                JsonObject serializedControlObject = serializedControl.getAsJsonObject();
                                boolean isMouse = serializedControlObject.get (MOUSE_FLAG_NAME).getAsBoolean();
                                String value = serializedControlObject.get(CONTROL_VALUE_NAME).getAsString();
                                if (isMouse) {
                                    try {
                                        MouseButton mouseButton = MouseButton.valueOf(value);
                                        inner.add(endControl, new CurrentControl(mouseButton));
                                    } catch (IllegalArgumentException e) {
                                        //
                                    }

                                }
                                else {
                                    try {
                                        KeyCode keyCode = KeyCode.valueOf(value);
                                        inner.add(endControl, new CurrentControl(keyCode));
                                    } catch (IllegalArgumentException e) {
                                        //
                                    }
                                }
                            }
                        });
                        controller.addEntry (mod, inner);
                    }
                });
                //map.entrySet().forEach(sje -> {
                //    String mod = sje.getKey();
                //    JsonElement modContent = sje.getValue();
//
                //});
                return controller;
            }
            throw new JsonParseException("Expected JSON Object for CurrentModsController");
        }
    }
}