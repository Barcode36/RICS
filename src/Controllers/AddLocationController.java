package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Location;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * Handles Actions for AddLocation.fxml
 */
public class AddLocationController
{


    @FXML
    private JFXTextField txt_locationId;



    /**
     * Adds new Location to DB
     */
    @FXML
    private void on_addClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Location> locs = dbm.loadLocations();
        Window window = txt_locationId.getScene().getWindow();

        if(txt_locationId.getText().equals(""))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Missing Information", "please " +
                    "enter a Location ID.");
            return;
        }

        else if (!Location.containsLocation(locs, txt_locationId.getText()))
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

    /**
     * Closes AddLocation.fxml
     */
    @FXML
    private void closeAddLocation() {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/LandingPage.fxml"));
        Stage homeStage = new Stage();
        homeStage.setTitle("RICS 1.0 Landing Page");
        homeStage.initStyle(StageStyle.TRANSPARENT);
        homeStage.setScene(new Scene(loader.load()));
        LandingPageController controller = loader.getController();
        controller.initData(Main.user);
        homeStage.show();*/
        Stage stage = (Stage) txt_locationId.getScene().getWindow();
        stage.close();
    }
}
