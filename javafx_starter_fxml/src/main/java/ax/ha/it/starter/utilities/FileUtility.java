package ax.ha.it.starter.utilities;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {

    /**
     * Creates a new File (currently not in use)
     * @param filePath
     * @return
     */
    @Nullable
    public static File createNewFile(String filePath) {
        try {
            File newFolder = new File(filePath);
            newFolder.createNewFile();
            return newFolder;
        } catch (IOException e) {
            String errorMessage = "Can't Create new file in this path";
            System.out.print(errorMessage);
            return null;
        }
    }

    /**
     * Creates a new File
     * @return
     */
    @Nullable
    public static File createNewFileWithoutPath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a location to save the new file in");
        try {
            File newFolder = new File(fileChooser.showSaveDialog(null).getAbsolutePath());
            newFolder.createNewFile();
            return (newFolder);
        } catch (IOException e) {
            String errorMessage = "Can't Create new file in this path";
            System.out.print(errorMessage);
            return null;
        }
    }

    /**
     * Creates a new folder (Currently not in use)
     * @param filePath
     */
    @Nullable
    public static File createNewFolder(String filePath) {
        File newFolder = new File(filePath);
        newFolder.mkdir();
        return newFolder;
    }

    /**
     * Opens the requested File
     * @param title
     */
    @Nullable
    public static File openSourceFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser.showOpenDialog(null);
    }

    /**
     * Save the file (Currently not in use)
     * @param title
     * @return a dialog to save
     */
    @Nullable
    public static File saveSourceFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser.showSaveDialog(null);
    }

    /**
     * OverWrites the opened file with updated text
     * @param file
     * @param content
     */
    public static void updateContent(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            System.out.println("File update completed");
            fileWriter.close();
        } catch (IOException iox) {
            System.out.println("File save failed.");
        }
    }

    /**
     * Delete the requested file (Currently not in use)
     * @param file
     */

    public static void deleteFile(File file) {
        file.deleteOnExit();
    }
}
