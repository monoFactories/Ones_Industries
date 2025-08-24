package core.moding.mod;

import java.io.File;

public record LoadingModParameter(File file, String[] dependencies, String modName) {
}
