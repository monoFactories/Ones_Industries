package core.moding.loading;

import com.google.gson.Gson;
import core.gameActions.Debug;
import core.moding.ModParameter;
import core.moding.mod.LoadingModParameter;
import core.moding.mod.Mod;
import game_logic.Game;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class ModInputStream {
    public abstract Mod getMod(File f);
    public abstract LoadingModParameter readLoadingParameter(File f);

    private static final ModInputStream current = new ModInputStream() {
        private final CustomLoader loader = new CustomLoader(new URL[]{});
        @Override
        public Mod getMod(File modFile) {
            Debug.debug("start loading mod for path \"" + modFile + "\"");
            if (modFile == null || !modFile.exists() || !modFile.isFile())
                return null;
            try(ZipFile modJar = getZipFile(modFile)) {
                try {
                    try {
                        loader.addURL(modFile.toURI().toURL());
                    } catch (Throwable e) {
                        Debug.debug(Level.WARNING, "", e);
                    }
                    ModInfoRecord infoRecord = readInfoRecord(modJar);
                    String modName = infoRecord.name();
                    Class<?> main = loader.loadClass(infoRecord.pathToStartClass());
                    if (Mod.class.isAssignableFrom(main)) {
                        Debug.debug("start load mod \"" + modName + "\"");
                        ModParameter parameter = new ModParameter(modName, modFile);
                        Constructor<?> constructor = main.getConstructor(ModParameter.class);
                        Debug.debug("load mod \"" + modName + "\"");
                        Mod mod = (Mod) constructor.newInstance(parameter);
                        Enumeration<? extends ZipEntry> entries = modJar.entries();
                        Iterator<? extends  ZipEntry> entryIterator = entries.asIterator();
                        Debug.debug("start loading all classes from mod: " + modName);
                        while (entryIterator.hasNext()) {
                            ZipEntry entry = entryIterator.next();
                            if (entry.getName().endsWith(".class")) {
                                StringBuilder lb = new StringBuilder("\t[");
                                lb.append(modName);
                                lb.append("] trying load class: ");
                                lb.append(entry.getName());
                                String nameClass = entry.getName().replace(".class", "").replace('/', '.');
                                try {
                                    Class<?> loading = Class.forName(nameClass, true, loader);
                                    lb.append(" - LUCK");
                                } catch (ClassNotFoundException | NoClassDefFoundError classNotFound) {
                                    lb.append(" - FAIL");
                                }
                                Debug.debug(lb.toString());
                            }
                        }
                        return mod;
                    }
                } catch (Throwable e) {
                    Debug.debug(Level.WARNING, "", e);
                }


            } catch (IOException io) {
                Game.log(Level.WARNING, "" + modFile, io);
            }
            return null;
        }

        @Override
        public LoadingModParameter readLoadingParameter(File f) {
            try (ZipFile z = getZipFile(f)) {
                ModInfoRecord info = readInfoRecord(z);
                return new LoadingModParameter(f, info.dependencies(), info.name());
            } catch (Exception io) {
                Debug.debug(Level.INFO, "", io);
            }
            return null;
        }

        private ZipFile getZipFile(File f) throws IOException {
            try {
                return new ZipFile(f);
            } catch (IOException io) {
                Debug.debug("couldn't create zip file for file: " + f);
            }
            throw new IOException("zip file not created");
        }

        private ModInfoRecord readInfoRecord(ZipFile z) {
            ZipEntry info = z.getEntry("info.json");
            if (info == null) {
                Game.addLog ("Archive: [" + z.getName() + "] don't have infoFile: \"info.json\"");
            } else {
                try (InputStream infoStream = z.getInputStream(info);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(infoStream))) {
                    Debug.debug("start decoding info.json for file: " + z);
                    Gson infoJson = new Gson();
                    try {
                        return infoJson.fromJson(reader, ModInfoRecord.class);
                    } catch (Exception e) {
                        Debug.debug("couldn't deserialize info.json for mod with path: " + z);
                    }
                } catch (IOException io2) {
                    Debug.debug("couldn't create info stream reader");
                }
            }
            throw new IllegalStateException("couldn't get the ModInfoRecord");
        }
        static class CustomLoader extends URLClassLoader {

            public CustomLoader(URL[] urls) {
                super(urls);
            }

            @Override
            protected void addURL(URL url) {
                super.addURL(url);
            }
        }
    };
    public static ModInputStream getModInputStream() {
        return current;
    }
    
    /*public Mod getMode (File modFile) {
        Debug.debug("start loading mod for path \"" + modFile + "\"");
        if (modFile == null || !modFile.exists() || !modFile.isFile())
            return null;
        try(ZipFile modJar = new ZipFile(modFile);
            URLClassLoader loader = new URLClassLoader(new URL[]{modFile.toURI().toURL()})) {
            ZipEntry info = modJar.getEntry("info.cfg");
            if (info == null) {
                Game.addLog ("Archive don't have infoFile: \"info.cfg\"");
                return null;
            }
            ModParameter parameter;
            try (InputStream infoStream = modJar.getInputStream(info);
                 BufferedReader reader = new BufferedReader (new InputStreamReader(infoStream))) {
                Debug.debug("start decoding \"" + modFile + "\"");
                Stream<String> linesInFile = reader.lines();
                InfoClassDescriptor descriptor = decodingInfoFile(linesInFile);
                String modName = descriptor.name;
                Class<?> main = loader.loadClass(descriptor.pathToMain);
                if (Mod.class.isAssignableFrom(main)) {
                    Debug.debug("start create mod \"" + modName + "\"");
                    parameter = new ModParameter(modName, modFile);
                    Constructor<?> constructor = main.getConstructor(ModParameter.class);
                    Debug.debug("download mod \"" + modName + "\"");
                    Mod mod = (Mod) constructor.newInstance(parameter);
                    Enumeration<? extends ZipEntry> entries = modJar.entries();
                    Iterator<? extends  ZipEntry> entryIterator = entries.asIterator();
                    while (entryIterator.hasNext()) {
                        ZipEntry entry = entryIterator.next();
                        if (entry.getName().endsWith(".class")) {
                            Debug.debug("trying load class \"" + entry.getName() + "\" from mod: " + modName);
                            String nameClass = entry.getName().replace(".class", "").replace('/', '.');
                            try {
                                Class<?> loading = Class.forName(nameClass, true, loader);
                                Debug.debug("class: " + loading.getPackageName() + " was load");
                            } catch (ClassNotFoundException | NoClassDefFoundError classNotFound) {
                                Debug.debug("couldn't load class: " + nameClass);
                            }
                        }
                    }
                    return mod;
                }
            } catch (Exception e) {
                Game.log(Level.WARNING, "Couldn't read", e);
            }
        } catch (IOException io) {
            Game.log(Level.WARNING, "Couldn't create ZipFile for file:"+modFile, io);
        }
        return null;
    }
    private static InfoClassDescriptor decodingInfoFile (Stream<String> lines) {
        AtomicReference<String> name = new AtomicReference<>();
        AtomicReference<String> pathToMain = new AtomicReference<>();
        lines.forEach((line) -> {
            String modLine = line.replaceAll("\\s+","");
            String[] parts = modLine.split("=", 2);
            if (parts.length == 2) {
                String bef = parts[0];
                String aft = parts[1];
                switch (bef) {
                    case "name" :
                        name.set(aft);
                        break;
                    case "MainPath":
                        pathToMain.set(aft);
                        break;
                }
            }
        });
        return new InfoClassDescriptor(name.get(), pathToMain.get());
    }
    @Override
    public void close() throws Exception {

    }
    private record InfoClassDescriptor (String name, String pathToMain) {
        InfoClassDescriptor {
            if (name == null || pathToMain == null)
                throw new NullPointerException();
        }
    }*/
}
