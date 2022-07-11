package my.chess.organizer.file;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class BinaryFileUtils {

    public static void writeFile(Object obj, File file) {
        try (FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f)) {
            o.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readFile(File file, Class<T> clazz) {
        try (FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi)) {
            return clazz.cast(oi.readObject());
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException: " + file);
            return null;
        } catch (IOException e) {
            log.warn("IOException: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
