package core.moding.mod;

import core.gameActions.Debug;
import game_logic.Game;
import core.moding.ModParameter;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModInputStream implements AutoCloseable {

    public Mod getMode (File modFile) {
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
                    Enumeration<? extends ZipEntry> entries = modJar.entries();
                    Iterator<? extends  ZipEntry> entryIterator = entries.asIterator();
                    while (entryIterator.hasNext()) {
                        ZipEntry entry = entryIterator.next();
                        if (entry.getName().endsWith(".class")) {
                            Debug.debug("trying load class \"" + entry.getName() + "\" from mod: " + modName);
                            String nameClass = entry.getName().replace(".class", "").replace('/', '.');
                            try {
                                Class<?> loading = loader.loadClass(nameClass);
                                Debug.debug("class: " + loading.getPackageName() + " was load");
                            } catch (ClassNotFoundException | NoClassDefFoundError classNotFound) {
                                Debug.debug("couldn't load class: " + nameClass);
                            }
                        }
                    }
                    return (Mod) constructor.newInstance(parameter);
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
    }
}
