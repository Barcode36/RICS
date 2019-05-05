package Models;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXButton btn_login;

    @FXML
    private AnchorPane root;

    public static AnchorPane rootP;


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }





    @FXML
    private void onClick_btn_login(javafx.event.ActionEvent actionEvent) throws IOException
    {
        try
        {
            String uname = txt_username.getText();
            String pword = txt_password.getText();
            Window window = btn_login.getScene().getWindow();

            if (txt_username.getText().isEmpty() || txt_password.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Incomplete information", "Please complete both " +
                        "fields");

                return;
            }

            DBManager dbm = new DBManager();
            User u = dbm.login(uname, pword);

            if (u == null) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid login", "Please check and re-enter your " +
                        "details");

            }
            else {
                Stage landingPageStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
                Models.LandingPageController landing = new LandingPageController();
                landing.setLabel(uname);
                Scene scene = new Scene(root);
                landingPageStage.setScene(scene);
                landingPageStage.setTitle("RICS 1.0 Landing Page");
                landingPageStage.initStyle(StageStyle.TRANSPARENT);
                landingPageStage.show();
                closeLogin();

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }




    }

    public void closeLogin() {
       Stage stage1 = (Stage)btn_login.getScene().getWindow();
        stage1.close();
    }

}
