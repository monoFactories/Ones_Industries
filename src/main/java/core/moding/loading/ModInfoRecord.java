package core.moding.loading;

public record ModInfoRecord(String name, int version, String pathToStartClass, String[] dependencies) {
}
