package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Location;
import Models.Part;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles Actions For Updating  select Part Information
 */
public class UpdatePartController implements Initializable {
    @FXML
    private JFXTextField txt_partNoun;

    @FXML
    private Label lbl_part;

    @FXML
    private JFXTextArea txt_description;

    @FXML
    private JFXTextField txt_cost;

    @FXML
    private JFXTextField txt_min;

    @FXML
    private JFXTextField txt_max;

    @FXML
    private JFXComboBox<Location> combo_location;

    @FXML
    private JFXButton btn_cancel;


    /**
     * Initialises UpdatePart.fxml
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Sets the partNumber label
     *
     * @param partNo
     */
    @FXML
    public void setLabel(String partNo) {
        lbl_part.setText(partNo);
    }

    /**
     * Initalises the Part Info fields
     *
     * @param p
     */
    @FXML
    protected void initData(String p) {
        try {
            DBManager dbm = new DBManager();
            ObservableList<Location> locations = dbm.loadLocations();
            Part part = Part.returnPart(p);
            txt_partNoun.setText(part.getPartNoun());
            txt_description.setText(part.getDescription());
            txt_cost.setText(String.valueOf(part.getUnitCost()));
            txt_min.setText(String.valueOf(part.getMinRecVal()));
            txt_max.setText(String.valueOf(part.getMaxRecVal()));
            combo_location.setItems(locations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the updated part to the DB Parts Table
     */
    @FXML
    private void on_saveClick() {

        Window window = btn_cancel.getScene().getWindow();
        String partNo = "";
        String partNoun = "";
        String description = "";
        Double cost = 0.0;
        int min = 0;
        int max = 0;
        Location location;
        String locationId = "";

        try {
            partNo = lbl_part.getText();
            partNoun = txt_partNoun.getText();
            description = txt_description.getText();
            cost = Double.parseDouble(txt_cost.getText());
            min = Integer.parseInt(txt_min.getText());
            max = Integer.parseInt(txt_min.getText());
            location = combo_location.getSelectionModel().getSelectedItem();
            locationId = location.getLocationId();
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing Information", "please ensure all fields " +
                    "are completed");
            e.printStackTrace();
        }


        if (partNoun.isEmpty() || description.isEmpty() || txt_cost.getText().equals("") || txt_min.getText().equals("") || txt_max.getText().equals("") || locationId.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing Information", "please ensure all fields " +
                    "are completed");

            return;
        } else if (min > max || min < 0) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Invalid Information", "please ensure the min is " +
                    "a valid number and less than the max");

            return;
        } else {
            try {
                DBManager dbm = new DBManager();
                ObservableList<Part> partsOBS = dbm.loadParts();

                for (Part part : partsOBS) {
                    if (part.getPartNumber().equals(partNo)) {
                        //accountCode, vendorPN, vendorId, partNoun, description, minLvl, maxLvl, cost, locationI
                        Part uPart = new Part(partNo, partNoun, description, min, max, cost, locationId);
                        dbm.updatePart(uPart);
                        closeUpdatePart();
                        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Part Updated", "you have " +
                                "successfully updated the stock card for " + partNo + ".");
                        return;

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Closes UpdatePart.fxml
     */
    @FXML
    private void closeUpdatePart() {
        try {
            Stage partsStage = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("../Views/PartMaster.fxml"));
            Scene scene1 = new Scene(root1);
            partsStage.setScene(scene1);
            partsStage.setTitle("RICS 1.0 Part Master");
            partsStage.initStyle(StageStyle.TRANSPARENT);
            partsStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
