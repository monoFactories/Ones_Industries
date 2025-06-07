package core.management.functions;

public interface DataSerializer<T> {
    byte[] toData (T t);
}
