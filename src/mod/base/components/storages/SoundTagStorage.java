package mod.base.components.storages;

import core.gameActions.Debug;
import game_logic.repositories.Identifier;
import mod.base.modification.Base;
import mod.base.modification.repositories.sounds.SoundRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SoundTagStorage {

    private static final ConcurrentHashMap<String, TagEntry> haveTagsMap = new ConcurrentHashMap<>();

    public static void parseSounds () {
        SoundRepository.sounds.forEach((modName, mediaRepository) -> mediaRepository.forEach((id, mediaTag) -> {
            String[] tags = mediaTag.tags();
            if (tags.length > 0) {
                Identifier identifier = mediaTag.id();
                for (String tag : tags) {
                    TagEntry entry = haveTagsMap.computeIfAbsent(tag, TagEntry::new);
                    entry.addID(identifier);
                }
            }
        }));
    }
    public static Identifier getNextID (String tag) {
        if (tag != null) {
            Base.getMod().debug ("all entries in tag storage: " + haveTagsMap);
            TagEntry entry = haveTagsMap.get(tag);
            if (entry != null)
                return entry.getNextID();
        }
        return null;
    }
    private static class TagEntry {
        public final String tag;
        private final List<Identifier> ids;
        private int counter;
        TagEntry (String tag) {
            if (tag == null)
                throw new NullPointerException("Tag to sound couldn't be null");
            this.tag = tag;
            ids = new ArrayList<>();
            counter = 0;
        }
        void addID (Identifier id) {
            if (id != null) {
                ids.add(id);
            }
        }
        Identifier getNextID () {
            if (!ids.isEmpty()) {
                Identifier nextId = ids.get(counter);
                counter = (counter + 1) % ids.size();
                Base.getMod().debug("get media with id : " + nextId);
                return nextId;
            }
            return null;
        }
        public String toString () {
            StringBuilder sb = new StringBuilder("[\n\ttag = \"").append(tag).append("\",\n\tids: [");
            StringBuilder listBuilder = new StringBuilder();
            ids.forEach(identifier -> listBuilder.append("\t\t").append('"').append(identifier).append("\",\n"));
            if (!ids.isEmpty()) {
                String s = listBuilder.substring (0, listBuilder.length() - 2);
                sb.append('\n').append(s).append("\n\t");
            }
            sb.append("],\n\tcounter: ").append(counter).append("]");
            return sb.toString();
        }
    }
}
