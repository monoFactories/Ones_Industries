package core.management;

import core.gameActions.Debug;
import game_logic.repositories.Identifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class DualRepository <T> {
    protected ConcurrentHashMap<String, SingleRepository<T>> repository = new ConcurrentHashMap<>();

    public void addEntry (String entryName, SingleRepository<T> t) {
        Debug.debug(() -> "add in DualRepository new entry, with name: " + entryName + ", with content: [\n" + t + "]");
        if (entryName != null)
            repository.put(entryName, t);
    }
    public void removeEntry (String entryName) {
        if (entryName != null) {
            Debug.debug("remove from DualRepository entry, with name: " + entryName);
            repository.remove(entryName);
        }
    }
    public SingleRepository<T> getEntry (String entryName) {
        if (entryName != null)
            return repository.get(entryName);
        return null;
    }

    //public void add (Identifier identifier, T t) {
    //    if (t != null && identifier != null) {
    //        Debug.debug("add in DualRepository new key-value with address: \"" + identifier + "\", with value: " + t);
    //        SingleRepository <T> c = repository.get(identifier.getSpace());
    //        if (c != null) {
    //            c.endRepository.put(identifier.getName(), t);
    //        }
    //    }
    //}
    public void add (Identifier identifier, T t) {
        if (t != null && identifier != null) {
            Debug.debug("add in DualRepository new key-value with address: \"" + identifier + "\", with value: " + t);
            SingleRepository <T> c = repository.computeIfAbsent(identifier.getSpace(), k -> new SingleRepository<>());
            c.endRepository.put (identifier.getName(), t);
        }
    }
    public void remove (Identifier id) {;
        if (id != null) {
            Debug.debug("remove from DualRepository key-value with address: \"" + id + "\"");
            SingleRepository<T> c = repository.get(id.getSpace());
            if (c != null) {
                c.endRepository.remove(id.getName());
            }
        }
    }
    public T get (Identifier id) {
        if (id != null) {
            SingleRepository<T> c = repository.get (id.getSpace());
            if (c != null) {
                return c.endRepository.get(id.getName());
            }
        }
        return null;
    }
    public void forEach (BiConsumer<String, SingleRepository<T>> act) {
        if (act != null) {
            repository.forEach(act);
        }
    }
    public int count () {
        return repository.size();
    }
    @Override
    public String toString() {
        StringBuilder complete = new StringBuilder("dualRepository [");
        repository.forEach((s, sgr) -> {
            complete.append("\t").append(s).append(": [\n");

            String[] splitRepo = sgr.toString().split("\n");
            for (String sp : splitRepo) {
                complete.append("\t\t").append(sp).append("\n");
            }
            complete.append("\t]\n");
        });
        complete.append("]");
        return complete.toString();
    }
    public static class SingleRepository<T> implements AbstractSingleRepository<T> {
        protected ConcurrentHashMap<String, T> endRepository = new ConcurrentHashMap<>();
        public void add (String s, T t) {
            if (t != null && s != null) {
                add (new Identifier(s), t);
            }
        }
        public void remove (String s) {
            if (s != null) {
                remove(new Identifier(s));
            }
        }
        public T get(String s) {
            return s != null ? get(new Identifier(s)) : null;
        }
        public void add (Identifier id, T t) {
            if (t != null && id != null) {
                Debug.debug("add new key-value in SingleRepository - {" + id.getName() + ": " + t + "}");
                String address = id.getName();
                endRepository.put(address, t);
            }
        }
        public void remove (Identifier id) {
            if (id != null) {
                String address = id.getName();
                Debug.debug("remove from SingleRepository element with id: " + address);
                endRepository.remove(address);
            }
        }
        public T get(Identifier id) {
            if (id != null)
                return endRepository.get(id.getName());
            return null;
        }
        public void forEach (BiConsumer<String, T> act) {
            if (act != null) {
                endRepository.forEach(act);
            }
        }
        public void clear () {
            endRepository.clear();
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            endRepository.forEach((s, t) -> sb.append(s).append(": ").append(t.toString()).append("\n"));
            return sb.toString();
        }
        public int count () {
            return endRepository.size();
        }
    }
    public interface AbstractSingleRepository<T> {
        void add (String s, T t) ;
        void remove (String s) ;
        T get(String s) ;
        void add (Identifier id, T t) ;
        void remove (Identifier id) ;
        T get(Identifier id) ;
        void forEach (BiConsumer<String, T> act) ;
        void clear ();
        int count ();
    }
}