package ax.ha.it.starter;

import ax.ha.it.starter.utilities.DialogUtility;
import ax.ha.it.starter.utilities.FileUtility;
import ax.ha.it.starter.utilities.SourceUtility;
import ax.ha.it.starter.utilities.TreeViewUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppController {

    private static final int THREADS_AVAILABLE = Runtime.getRuntime().availableProcessors();
    //FX Views
    @FXML
    private TextArea resultTextArea;
    @FXML
    public TabPane codeAreaLayout;

    //File Menu Items
    @FXML
    private MenuItem openFileMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    /*
    @FXML
    private MenuItem saveMenuItem;
    */
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem newFileMenuItem;
    @FXML
    private MenuItem newFolderMenuItem;

    private ExecutorService executorService;
    @FXML
    private TreeView<SourceUtility> fileTreeView;
    @FXML
    private TreeItem<SourceUtility> treeItem;

    private TreeViewUtility treeViewList;



    @FXML
    private void kill() {
        System.exit(0);
    }

    public void initialize() {
        onMenuItemsActions();
        treeViewList = new TreeViewUtility(fileTreeView, treeItem);
        executorService = Executors.newFixedThreadPool(THREADS_AVAILABLE);
        codeAreaLayout.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        //Editor.openWindowsTerminal();
    }

    private void onMenuItemsActions() {
        exitMenuItem.setOnAction(event -> kill());
        openFileMenuItem.setOnAction(event -> openFileAction());
        //saveMenuItem.setOnAction(event -> saveFileAction());
        aboutMenuItem.setOnAction(actionEvent -> DialogUtility.openAlertDialog("Created by Anton Wärnström and Andreas Tallberg")); // TODO: Add names to the string
        newFileMenuItem.setOnAction(event -> createNewFile(Objects.requireNonNull(FileUtility.createNewFileWithoutPath())));

    }

    /**
     * @description Opens fileexplorer and calls openNewTabWithFile() to send the returned file
     * @param
     * @return
     * <p>
     * TODO: Check for only Java extensions
     */
    private void openFileAction() {
        File javaFile = FileUtility.openSourceFile("Find and select Java file");
        if (javaFile != null) {
            executorService.execute(() -> openNewTabWithFile(javaFile));
        }
    }

    /*
    * Currently not needed
    * Issues with file structure causing us to not having access to the updated version of the code
    private void saveFileAction() {
        File javaFile = FileUtility.saveSourceFile("Select and save Java file");
        if (javaFile != null) {
            executorService.execute(() -> {
                    //FileUtility.updateContent(javaFile);
            });
        }
    }*/

    /**
     * @param sourceFile, fileManager
     * @description creates a new tab based on a file
     */
    public void openNewTabWithFile(File sourceFile) {
        Tab newTab = new Tab(sourceFile.getName());
        newTab.setUserData(sourceFile.getPath());

        CodeArea codeTextArea = new CodeArea();
        Editor editorController = new Editor(codeTextArea, resultTextArea);

        try {
            editorController.codeAreaHighlighter();
            StringBuilder code = new StringBuilder();
            List<String> codeLines = Files.readAllLines(Path.of(sourceFile.getPath()), Charset.defaultCharset());
            for (String s : codeLines) {
                code.append(s).append("\n");
            }
            codeTextArea.replaceText(0, 0, code.toString());
            ScrollPane scrollArea = new ScrollPane(codeTextArea);
            newTab.setContent(scrollArea);
            scrollArea.fitToWidthProperty().set(true);
            scrollArea.fitToHeightProperty().set(true);
            editorController.updateSourceFile(sourceFile);
            Platform.runLater(() -> {
                treeViewList.addTreeItemWithValue(new SourceUtility(sourceFile));
                codeAreaLayout.getTabs().add(newTab);
            });
        } catch (IOException e) {
            DialogUtility.openAlertDialog("Couldn't find a file to open");
        }
    }

    /**
     * Creates a new File with a new name
     * @param sourceFile
     */
    private void createNewFile(File sourceFile) {
        String fileName = sourceFile.getName();
        if (!fileName.isEmpty()) {
            Tab newTab = new Tab(fileName);
            CodeArea codeTextArea = new CodeArea();
            Editor editorController = new Editor(codeTextArea, resultTextArea);
            ScrollPane scrollArea = new ScrollPane(codeTextArea);

            editorController.codeAreaHighlighter();
            newTab.setContent(scrollArea);
            scrollArea.fitToWidthProperty().set(true);
            scrollArea.fitToHeightProperty().set(true);
            editorController.updateSourceFile(sourceFile);
            Platform.runLater(() -> {
                treeViewList.addTreeItem(new SourceUtility(sourceFile));
                codeAreaLayout.getTabs().add(newTab);
            });
        } else {
            DialogUtility.openAlertDialog("Please enter a filename");
        }
    }


    /*
    * Currently not needed
    * Issues with file structure causing us to not having access to the updated version of the code
    private void saveToSourceCode(File chosenFile) throws IOException {
        String code = "test";
        try {
            FileWriter fileWriter = new FileWriter(chosenFile);
            fileWriter.write(code);
            fileWriter.close();
        } catch (IOException iox) {
            String errorMessage = "Can't Save this file, Please make sure this file not deleted";
            DialogUtility.openAlertDialog(errorMessage);
        }
    }*/
}
