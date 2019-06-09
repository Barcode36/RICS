package Models;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * AlertHelper Class for displaying Error/Confirmation messages to user
 */
public class AlertHelper {

    /**
     * Constructor for showing Alertz
     * @param alertType Confirm, error, warning etc
     * @param window
     * @param title alert title
     * @param message message display in alert
     */
    public static void showAlert(Alert.AlertType alertType, Window window, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}
