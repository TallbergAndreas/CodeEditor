package ax.ha.it.starter.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class DialogUtility {

    /**
     * @description opens an Alert
     * @param content
     */
    public static void openAlertDialog(String content) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setContentText(content);
        a.getDialogPane().getButtonTypes().add(ButtonType.OK);
        a.show();
    }

    /**
     * @description Opens a dialog where you can set a name for your file/tab
     * @param title
     * @return the name for your file/tab
     */
    public static String inputDialog(String title) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setContentText(title);
        dialog.setHeaderText(null);
        dialog.setTitle(null);
        dialog.setGraphic(null);
        Optional<String> result =  dialog.showAndWait();
        return result.orElse(null);
    }
}
