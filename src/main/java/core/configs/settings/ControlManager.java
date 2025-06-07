package core.configs.settings;

import javafx.scene.input.MouseButton;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ControlManager {
    private static final ConcurrentHashMap<MouseButton, String> constantActionsMouse = new ConcurrentHashMap<>();

    public static class ControlEntry {
        private String name;
        private String mod;
        private String id;
        public ControlEntry (String name, String mod, String id) {
            this.name = name;
            this.mod = mod;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getMod() {
            return mod;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return mod + ":" + id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ControlEntry that)) return false;
            return Objects.equals(name, that.name) && Objects.equals(mod, that.mod) && Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return mod.hashCode() * 32768 + id.hashCode();
        }
    }
}
