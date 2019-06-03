package Controllers;


import Models.AlertHelper;
import Models.DBManager;
import Models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Handles Login Action
 */
public class LoginController {

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXButton btn_login;


    /**
     * Validates User Login & Navigates to LandingPage.fxml on success
     */
    @FXML
    private void onClick_btn_login() {
        try {
            String uname = txt_username.getText();
            String pword = txt_password.getText();
            Window window = btn_login.getScene().getWindow();

            if (txt_username.getText().isEmpty() || txt_password.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Missing information", "Please complete both " +
                        "fields");

                return;
            }

            DBManager dbm = new DBManager();
            User u = dbm.login(uname, pword);

            if (u == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid login", "Re-enter your details");

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/LandingPage.fxml"));
                Stage homeStage = new Stage();
                homeStage.setTitle("RICS 1.0 Issue Part");
                homeStage.initStyle(StageStyle.TRANSPARENT);
                homeStage.setScene(new Scene(loader.load()));
                LandingPageController controller = loader.getController();
                controller.initData(u);

                homeStage.show();
                closeLogin();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the application
     */
    public void closeLogin() {
        Stage stage1 = (Stage) btn_login.getScene().getWindow();
        stage1.close();
    }

}
