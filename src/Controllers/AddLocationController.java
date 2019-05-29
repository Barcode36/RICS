package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Location;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;



public class AddLocationController
{

    @FXML
    private JFXButton btn_cancel;

    @FXML
    private JFXTextField txt_locationId;


    @FXML
    private void on_addClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Location> locs = dbm.loadLocations();
        Window window = btn_cancel.getScene().getWindow();

        if (!Location.containsLocation(locs, txt_locationId.getText()))
        {
            try
            {
                Location loc = new Location(txt_locationId.getText());
                dbm.addLocation(loc);

                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Location added", "you have " +
                        "successfully created a new stock location");
                return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Location exists", "This location " +
                    "already exists");
            return;
        }
    }

    @FXML
    private void closeAddLocation()
    {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();
    }
}
