package core.configs.languages;

import com.google.gson.*;
import core.gameActions.Debug;
import game_logic.repositories.LanguageRepository;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public class LanguageJson implements JsonSerializer<LanguageRepository>, JsonDeserializer<LanguageRepository> {
    public static final LanguageJson ADAPTER = new LanguageJson();
    public static final String NAME_TAG = "name";
    @Override
    public JsonElement serialize(LanguageRepository language, Type type, JsonSerializationContext jsonSerializationContext) {
        if (language == null)
            throw new NullPointerException("LanguageRepository is null");
        JsonObject languageJson = new JsonObject();
        languageJson.addProperty(NAME_TAG, language.getLanguageName());
        language.getLanguageParts().forEach((partName, part) -> {
            JsonObject partJson = new JsonObject();
            part.forEach(partJson::addProperty);
            languageJson.add(partName, partJson);
        });
        return languageJson;
    }
    @Override
    public LanguageRepository deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonElement nameElement = jsonObject.get(NAME_TAG);
        if (nameElement == null || !nameElement.isJsonPrimitive() || !nameElement.getAsJsonPrimitive().isString()) {
            throw new JsonParseException("Missing or invalid 'name' property");
        }
        String name = nameElement.getAsString();
        Debug.debug("language deserialization: name = " + name);
        LanguageRepository languageRepository = new LanguageRepository();
        languageRepository.setLanguageName(name);
        int[] name_fixer = new int[] {0};
        jsonObject.asMap().forEach ((partName, part) -> {
            if (partName.equals(NAME_TAG) && name_fixer[0] == 0) {
                name_fixer[0]++;
                return;
            }
            if (part instanceof JsonObject jsonPart) {
                ConcurrentHashMap <String, String> map = new ConcurrentHashMap<>();
                jsonPart.asMap().forEach((nameWord, word) -> {
                    if (word instanceof JsonPrimitive primitive) {
                        if (primitive.isString()) {
                            map.put (nameWord, primitive.getAsString());
                        }
                    }
                });
                languageRepository.setLanguagePart(partName, map);
            }
        });
        return languageRepository;
    }
}