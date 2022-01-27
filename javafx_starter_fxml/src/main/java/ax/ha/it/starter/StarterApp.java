package ax.ha.it.starter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StarterApp extends Application {

    /**
     * gets all css and loads FXML in a window sized 1080x800
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApp.class.getResource("layout.fxml"));

        int windowWidth = 1080;
        int windowHeight = 800;
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);

        scene.getStylesheets().add("style.css");
        scene.getStylesheets().add("tab.css");
        scene.getStylesheets().add("keywords.css");
        scene.getStylesheets().add("treeview.css");

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}