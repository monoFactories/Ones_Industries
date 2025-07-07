package mod.base.components.worlds.level;

import mod.base.modification.data.worlds.level.AbstractLevel;

import java.util.function.Function;

public class LevelUtil {
    public static Function<Long, AbstractLevel> customLevel;
    public static void setCustomLevel(Function<Long, AbstractLevel> customLevel) {
        LevelUtil.customLevel = customLevel;
    }
    public static Function<Long, AbstractLevel> getCustomLevel() {
        return customLevel;
    }
    public static Function<Long, AbstractLevel> getStandardLevel() {
        return SimpleLevel::new;
    }
}
