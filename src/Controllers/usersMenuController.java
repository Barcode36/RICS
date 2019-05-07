package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class usersMenuController implements Initializable
{
    @FXML
    private JFXButton btn_addUser;

    @FXML
    private JFXButton btn_updateUser;

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXTextField txt_firstName;

    @FXML
    private JFXTextField txt_lastName;

    @FXML
    private JFXTextField txt_rig;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXPasswordField txt_passwordConfirm;

    @FXML
    private JFXRadioButton rdo_admin;

    @FXML
    private TableView<Map.Entry> tbl_users;

    @FXML
    private TableColumn<Map.Entry, String> col_username;

    @FXML
    private TableColumn<Map.Entry, String> col_firstName;

    @FXML
    private TableColumn<Map.Entry, String> col_lastName;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_delete;


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        txt_username.setDisable(true);

        //fade-in transition for menu buttons
        FadeTransition fadeIn1 = new FadeTransition();
        fadeIn1.setDuration(Duration.seconds(3));
        fadeIn1.setNode(btn_addUser);
        fadeIn1.setFromValue(0);
        fadeIn1.setToValue(1);
        fadeIn1.setCycleCount(1);

        FadeTransition fadeIn2 = new FadeTransition();
        fadeIn2.setDuration(Duration.seconds(3));
        fadeIn2.setNode(btn_updateUser);
        fadeIn2.setFromValue(0);
        fadeIn2.setToValue(1);
        fadeIn2.setCycleCount(1);

        fadeIn1.play();
        fadeIn2.play();


    }

    @FXML
    private void on_addUserClick()
    {
        txt_username.setDisable(false);
        txt_username.clear();
        txt_firstName.clear();
        txt_lastName.clear();
        txt_rig.clear();
        txt_password.clear();
        txt_passwordConfirm.clear();

    }

    @FXML
    private void on_updateUserClick()
    {
        DBManager dbm = new DBManager();


        HashMap<String, User> usersMap = dbm.loadUsers();


        ObservableList<String> keyList = FXCollections.observableArrayList(usersMap.keySet());

        ObservableList<User> valueList = FXCollections.observableArrayList(usersMap.values());

        ObservableList<Map.Entry> usersOBS = FXCollections.observableArrayList(usersMap.entrySet());


        col_username.setCellValueFactory(new PropertyValueFactory<Map.Entry, String>("username"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<Map.Entry, String>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<Map.Entry, String>("lastName"));
        //populate table view
        tbl_users.setItems(usersOBS);

        //disable username textfield - usernames are final
        txt_username.setDisable(true);
        txt_username.setText("username");
        txt_firstName.setText("first name");
        txt_lastName.setText("last name");
        txt_rig.setText("164");
        txt_password.setText("password");
        txt_passwordConfirm.setText("");
    }

    @FXML
    private void on_saveClick()
    {
        String username = txt_username.getText();
        String firstName = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String rig = txt_rig.getText();
        String password = txt_password.getText();
        String passwordC = txt_passwordConfirm.getText();
        Boolean admin = rdo_admin.isSelected();
        Window window = btn_addUser.getScene().getWindow();

        //field validation
        if(username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || rig.isEmpty() || password.isEmpty() || passwordC.isEmpty())
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Missing information", "Please complete all " +
                    "fields");

            return;
        }
        else if(!password.equals(passwordC))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Passwords", "Passwords do not match");

            return;
        }
        else
        {
            try
            {
                int rigNo = Integer.parseInt(rig);

                User u = new User(username, password, firstName, lastName, rigNo, admin);
                DBManager dbm = new DBManager();

                if(dbm.registerUser(u))
                {
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Account Created" , "You have " +
                            "successfully created " + username + "'s account.");

                    txt_username.clear();
                    txt_firstName.clear();
                    txt_lastName.clear();
                    txt_rig.clear();
                    txt_password.clear();
                    txt_passwordConfirm.clear();
                    rdo_admin.setSelected(false);
                }
                else if (dbm.registerUser(u) == false)
                {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Unable to create user " +
                            "account.");

                    return;
                }


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
