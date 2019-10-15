package IO;

/**
 * Created by Daniel Ben Simon
 */

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileWriter {
    private static boolean isFileDestinationCreated = false;
    /**
     * Write a given string to file
     *
     * @param text some text
     * @param destinationFilePath the path of the destination file
     */
    public static void WriteFile(String text, String destinationFilePath) {
        try {
            FileUtils.writeStringToFile(new File(destinationFilePath), text);
        } catch (IOException e) {
            Console.PrintException("Error writing string to file", e);
        }
    }

    /**
     * Append a given string to file
     *
     * @param text some text
     * @param destinationFilePath the path of the destination file
     */
    public static void AppendFile(String text, String destinationFilePath) {
        try (java.io.FileWriter fw = new java.io.FileWriter(destinationFilePath, true)) {
            fw.write(text);
        } catch (IOException ex) {
            Console.PrintException(String.format("Error appending text to file: %s", destinationFilePath), ex);
        }
    }

    /**
     * Creates new file according to the given path
     *
     * @param destinationFilePath the path of the destination file
     */
    public static void CreateNewFile(String destinationFilePath) {
        File file = new java.io.File(destinationFilePath);
        isFileDestinationCreated = true;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                isFileDestinationCreated = false;
                Console.PrintException("Error creating new file!", e);
            }
        }
    }

    /**
     *
     * @return true if file destination created
     */
    public static boolean isFileCreated() {
        return isFileDestinationCreated;
    }
}
