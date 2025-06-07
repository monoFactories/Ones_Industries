package core.moding.data;

public interface SpecialRepository<K, V> {
    V get (K k);
    void add (V v);
    void clear();
    void append(SpecialRepository<K, V> appends);
}
