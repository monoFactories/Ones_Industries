package core.moding.mod;

import java.io.File;
import java.util.Arrays;

public record LoadingModParameter(File file, String[] dependencies, String modName) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LoadingModParameter: [\n\tfile: ");
        sb.append(file).append("\n\tdependencies: [\n");
        for (String d : dependencies) {
            sb.append("\t\"").append(d).append("\"\n");
        }
        sb.append("\t]\n\tname: \"").append(modName).append("\"\n]");
        return sb.toString();
    }
}
