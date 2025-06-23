package game_logic.repositories;

import game_logic.Game;

public final class Identifier implements Cloneable {
    public static final char STANDARD_SEPARATION_CHARACTER = ':';

    private final String space;
    private final String name;

    private Identifier(String name, String space, boolean notUsingVariable) {
        this.name = name;
        this.space = space;
    }
    public Identifier(String space, String name) {
        this(assertParameter(name), assertParameter(space), false);
    }
    private Identifier(String[] a) {
        this(a[0], a[1]);
    }
    public Identifier (String id) {
        this(parser(id, STANDARD_SEPARATION_CHARACTER));
    }

    public String getName() {
        return name;
    }

    public String getSpace() {
        return space;
    }

    @Override
    public Identifier clone() {
        try {
          return (Identifier) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Identifier id) return id.name.equals(name) && id.space.equals(space);
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * space.hashCode() + name.hashCode();
    }

    @Override
    public String toString() {
        return space + STANDARD_SEPARATION_CHARACTER + name;
    }

    private static String[] parser (String line, char separator) {
        String[] complete = new String[] {Game.GAME_NAME, line};
        int number = line.indexOf(separator);
        if (number >= 0) {
            complete[1] = line.substring(number + 1);
            if (number > 0) {
                complete[0] = line.substring(0, number);
            }
        }
        return complete;
    }

    static String assertParameter (String space) {
        if (!testParameter(space))
            throw new IllegalArgumentException("space: \"" + space + "\" should only store characters: 0-9, Aa-Zz, Аа-Яя and etc");
        return space;
    }
    static boolean testParameter (String space) {
        for (char a : space.toCharArray()) {
            if (!testChar(a))
                return false;
        }
        return true;
    }
    static boolean testChar (char a) {
        return a > 0x2F &&  (a < 0x450);
    }
}
