package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.net.URL;


import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UsersMenuController implements Initializable {
    @FXML
    private ImageView btn_addUser;

    @FXML
    private ImageView btn_refreshList;

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
    private TableView<User> tbl_users;

    @FXML
    private TableColumn<Map.Entry, String> col_username;

    @FXML
    private TableColumn<Map.Entry, String> col_firstName;

    @FXML
    private TableColumn<Map.Entry, String> col_lastName;

    @FXML
    private JFXButton btn_delete;

    private int userAlertCounter = 0;

    private DBManager dbm = new DBManager();


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        on_refreshListClick();

        try {
            tbl_users.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = tbl_users.getSelectionModel().getSelectedIndex();
                    User user = tbl_users.getItems().get(index);

                    txt_username.setDisable(true);
                    txt_username.setText(user.getUsername());
                    txt_firstName.setText(user.getFirstName());
                    txt_lastName.setText(user.getLastName());
                    txt_rig.setText(Integer.toString(user.getRig()));
                    rdo_admin.setSelected(user.getAdminUser());
                    txt_password.setText(user.getPassword());
                    txt_passwordConfirm.setText("");
                    userAlertCounter = 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_addUserClick() {
        txt_username.setDisable(false);
        txt_username.clear();
        txt_firstName.clear();
        txt_lastName.clear();
        txt_rig.clear();
        txt_password.clear();
        txt_passwordConfirm.clear();

    }

    @FXML
    private void on_refreshListClick() {
        DBManager dbm = new DBManager();
        ObservableList<User> usersOBS = dbm.loadUsersOBS();

        //populate table view
        tbl_users.setItems(usersOBS);

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));


        txt_username.setDisable(false);
        txt_username.setText("");
        txt_firstName.setText("");
        txt_lastName.setText("");
        txt_rig.setText("");
        txt_password.setText("");
        txt_passwordConfirm.setText("");
        rdo_admin.setSelected(false);
    }

    @FXML
    private void on_saveClick()
    {
        ObservableList<User> usersOBS = dbm.loadUsersOBS();
        String username = txt_username.getText();
        String firstName = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String rig = txt_rig.getText();
        String password = txt_password.getText();
        String passwordC = txt_passwordConfirm.getText();
        Boolean admin = rdo_admin.isSelected();
        Window window = btn_addUser.getScene().getWindow();

        //field validation
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || rig.isEmpty() || password.isEmpty() || passwordC.isEmpty())
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Missing information", "Please complete all " +
                    "fields");
            return;
        }
        else if (!password.equals(passwordC))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Passwords", "Passwords do not match");
        }
        else if (dbm.containsUser(usersOBS, username))
        {
            if (userAlertCounter == 0)
            {
                AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Username already exists", "To update " +
                        "account details click OK & save," + "to cancel click OK & undo.");
                return;
            }

            userAlertCounter++;

            if (userAlertCounter == 2)
            {
                try
                {
                    int rigNo = Integer.parseInt(rig);
                    User u = new User(username, password, firstName, lastName, rigNo, admin);

                    dbm.updateUser(u);
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Account Updated", "you have " +
                            "successfully updated account details");
                    return;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                userAlertCounter = 0;
            }
        }
        else if(!dbm.containsUser(usersOBS, username))
        {
            try {
                int rigNo = Integer.parseInt(rig);
                User u = new User(username, password, firstName, lastName, rigNo, admin);

                if (dbm.registerUser(u)) {

                    txt_username.clear();
                    txt_firstName.clear();
                    txt_lastName.clear();
                    txt_rig.clear();
                    txt_password.clear();
                    txt_passwordConfirm.clear();
                    rdo_admin.setSelected(false);

                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Account Created", "You have " +
                            "successfully created a new account.");

                    return;

                } else if (dbm.registerUser(u) == false) {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Unable to create user " +
                            "account.");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        on_refreshListClick();
    }

    @FXML
    private void on_undoClick()
    {
        try
        {
            ObservableList<User> usersOBS = dbm.loadUsersOBS();

            for(User user : usersOBS)
            {
                if(user.getUsername().equals(txt_username.getText()))
                {
                    txt_username.setText(user.getUsername());
                    txt_firstName.setText(user.getFirstName());
                    txt_lastName.setText(user.getLastName());
                    txt_rig.setText(Integer.toString(user.getRig()));
                    rdo_admin.setSelected(user.getAdminUser());
                    txt_password.setText(user.getPassword());
                    txt_passwordConfirm.setText("");

                    userAlertCounter =0;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_deleteClick()
    {
        Window window = btn_addUser.getScene().getWindow();

        if(userAlertCounter <= 0 && userAlertCounter >-1)
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Delete account?", "to delete " +
                    "user click delete again" + "to cancel click OK undo.");
            return;
        }
        userAlertCounter --;

        if(userAlertCounter == -2)
        {
            try
            {
                ObservableList<User> usersOBS = dbm.loadUsersOBS();

                for(User user : usersOBS)
                {
                    if(user.getUsername().equals(txt_username.getText()))
                    {
                        dbm.deleteUser(user);
                        userAlertCounter =0;
                        on_refreshListClick();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void on_homeClick()
    {
        try
        {
            Stage homeStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../Views/LandingPage.fxml"));
            Scene scene = new Scene(root);
            homeStage.setScene(scene);
            homeStage.setTitle("RICS 1.0 Home");
            homeStage.initStyle(StageStyle.TRANSPARENT);
            homeStage.show();
            closeUsersMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void closeUsersMenu()
    {
        Stage stage = (Stage)btn_delete.getScene().getWindow();
        stage.close();
    }
}
