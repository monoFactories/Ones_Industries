package game_logic.repositories;

import com.google.gson.*;
import game_logic.Game;

import java.lang.reflect.Type;

public final class Identifier implements Cloneable {
    public static final char STANDARD_SEPARATION_CHARACTER = ':';
    public static final IdentifierJson IDENTIFIER_JSON_ADAPTER = new IdentifierJson();

    private final String space;
    private final String name;

    private Identifier(String name, String space, boolean notUsingVariable) {
        this.name = name;
        this.space = space;
    }
    public Identifier(String space, String name) {
        this((name), (space), false);
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

    public static final class IdentifierJson implements JsonSerializer<Identifier>, JsonDeserializer<Identifier> {

        @Override
        public JsonElement serialize(Identifier identifier, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonArray jsonId = new JsonArray();
            jsonId.add (identifier.space);
            jsonId.add (identifier.name);
            return jsonId;
        }

        @Override
        public Identifier deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if (jsonArray.size() >= 2) {
                    String space = jsonArray.get(0).getAsString();
                    String name = jsonArray.get(1).getAsString();
                    return new Identifier (space, name);
                }
                throw new JsonParseException("Expected array of size >= 2 for Identifier");
            }
            throw new JsonParseException("Expected JSON array for Identifier");
        }
    }
}