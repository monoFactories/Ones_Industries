package core.moding.mod;

import core.gameActions.Debug;
import core.gameActions.FileManager;
import game_logic.Game;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;

public class ModFileManager {
    private final File pathToContent;

    public ModFileManager (String modName) {
        if (modName == null)
            throw new NullPointerException("mod name can not be null");
        pathToContent = FileManager.concatToDirectory("content", modName);
    }
    public void writeByteResource (String relativePath, byte[] data) {
        if (relativePath != null && data != null) {
            File endPath = createGetUnitedFile(relativePath);
            if (endPath == null) return;
            try (FileOutputStream fos = new FileOutputStream(endPath)) {
                fos.write(data);
            } catch (IOException | SecurityException e) {
                Debug.debug("couldn't write byte data to path when work file output stream: " + endPath);
            }
        }
        Debug.debug("couldn't write byte data");
    }
    public void writeStringsResource (String relativePath, List<String> data) {
        if (relativePath != null && data != null) {
            File endPath = createGetUnitedFile(relativePath);
            if (endPath == null) return;
            try (BufferedWriter fos = new BufferedWriter(new FileWriter(endPath))) {
                for (String s: data)
                    fos.write(s);
            } catch (IOException | SecurityException e) {
                Debug.debug("couldn't write strings data to path when work file output stream: " + endPath);
            }
        }
        Debug.debug("couldn't write strings data");
    }
    public void writeObjectResource (String relativePath, Object data) {
        if (relativePath != null && data != null) {
            File endPath = createGetUnitedFile(relativePath);
            if (endPath == null) return;
            try (ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(endPath))) {
                fos.writeObject(data);
            } catch (IOException | SecurityException e) {
                Debug.debug("couldn't write object data to path when work file output stream: " + endPath);
            }
        }
        Debug.debug("couldn't write object data");
    }
    public FileOutputStream getOutputStreamResource (String relativePath) {
        if (relativePath != null) {
            File endPath = createGetUnitedFile(relativePath);
            if (endPath == null) return null;
            try {
                return new FileOutputStream(endPath);
            } catch (IOException | SecurityException e) {
                Debug.debug("couldn't write data to path when work file output stream: " + endPath);
            }
        }
        Debug.debug("couldn't write data");
        return null;
    }
    public byte[] readByteResource (String path) {
        if (path != null) {
            File targetFile = unitedFile(path);
            byte[] bytes = null;
            try (FileInputStream fis = new FileInputStream(targetFile)) {
                bytes = fis.readAllBytes();
            } catch (IOException io) {
                Debug.debug("io exception when read bytes from file \"" + targetFile + "\"");
            }
            catch (SecurityException s) {
                Debug.debug("security exception when read bytes from \"" + targetFile + "\"");
            }
            return bytes;
        }
        return null;
    }
    public List<String> readStringsResource (String path) {
        if (path != null) {
            File targetFile = unitedFile(path);
            try (BufferedReader fis = new BufferedReader(new FileReader(targetFile))) {
                return fis.lines().toList();
            } catch (IOException io) {
                Debug.debug("io exception when read lines from file \"" + targetFile + "\"");
            }
        }
        return null;
    }
    public Object readObjectResource (String path) {
        if (path != null) {
            File targetFile = unitedFile(path);
            Object o = null;
            try (ObjectInputStream fis = new ObjectInputStream(new FileInputStream(targetFile))) {
                return fis.readObject();
            } catch (IOException io) {
                Debug.debug("io exception when read object from file \"" + targetFile + "\"");
            } catch (ClassNotFoundException e) {
                Debug.debug("class deserialization exception: " + e.getMessage());
            }
        }
        return null;
    }
    public InputStream getInputStreamResource (String path) {
        if (path != null) {
            File targetFile = unitedFile(path);
            try {
                return new FileInputStream(targetFile);
            } catch (IOException io) {
                Debug.debug("io exception when read bytes from file \"" + targetFile + "\"");
            }
            catch (SecurityException se) {
                Debug.debug("couldn't get input stream ");
            }
        }
        return null;
    }
    private File createGetUnitedFile (String s) {
        if (s != null) {
            File result = unitedFile(s);
            Path createdPath = result.toPath();
            Path createdPathParent = createdPath.getParent();
            if (createdPathParent != null && !Files.exists(createdPathParent)) {
                try {
                    Files.createDirectories(createdPathParent);
                } catch (IOException | SecurityException exc) {
                    Debug.debug("couldn't create directories for path \"" + createdPath);
                }
            }
            if (!Files.exists(createdPath)) {
                try {
                    Files.createFile(createdPath);
                } catch (IOException | SecurityException exc) {
                    Debug.debug("couldn't create file for path \"" + createdPath);
                }
            }

            return createdPath.toFile();
            //try {
            //    if (!result.exists()) {
            //        Debug.debug("creating path for file \"" + result + "\"");
            //        if (result.mkdirs()) {
            //            Debug.debug("folders created for file: " + result);
            //        }
            //        if (result.createNewFile()) {
            //            Debug.debug("create new file: " + result);
            //        }
            //    }
            //} catch (SecurityException | IOException se) {
            //    Game.log(Level.INFO, "couldn't create file: " + result, se);
            //}
            //return result;
        }
        return null;
    }
    public File unitedFile (String path) {
        if (path != null)
            return new File(pathToContent, path);
        return pathToContent;
    }
}
