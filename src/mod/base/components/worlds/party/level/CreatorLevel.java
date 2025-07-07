package mod.base.components.worlds.party.level;

import mod.base.components.worlds.level.LevelUtil;
import mod.base.modification.data.worlds.level.AbstractLevel;

public class CreatorLevel {
    public static AbstractLevel create (long seed) {
        try {
            return LevelUtil.getCustomLevel().apply(seed);
        } catch (Throwable Pu239) {
            return LevelUtil.getStandardLevel().apply(seed);
        }
    }
}
