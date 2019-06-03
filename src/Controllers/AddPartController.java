package Controllers;

import Models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Add Part Controller handles functionality for Adding new Parts to DB
 */
public class AddPartController implements Initializable {
    @FXML
    private JFXComboBox<InventoryAccount> combo_accCode;

    @FXML
    private JFXComboBox<Vendor> combo_vendor;

    @FXML
    private JFXComboBox<Location> combo_location;

    @FXML
    private JFXTextField txt_vendorPartNo;

    @FXML
    private JFXTextField txt_partNoun;

    @FXML
    private JFXTextField txt_cost;

    @FXML
    private JFXTextField txt_min;

    @FXML
    private JFXTextField txt_max;

    @FXML
    private JFXTextArea txt_description;

    @FXML
    private JFXButton btn_cancel;


    /**
     * Initalises combo boxes on AddPart.fxml
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBManager dbm = new DBManager();

        ObservableList<InventoryAccount> inventoryAccounts = dbm.loadInventoryAccounts();
        combo_accCode.setItems(inventoryAccounts);

        ObservableList<Vendor> vendors = dbm.loadVendors();
        combo_vendor.setItems(vendors);

        ObservableList<Location> locations = dbm.loadLocations();
        combo_location.setItems(locations);
    }

    /**
     * On clicking save adds new Part to DB using user inputs
     */
    @FXML
    private void on_saveClick() {
        Window window = btn_cancel.getScene().getWindow();

        try {

            DBManager dbm = new DBManager();

            InventoryAccount account = combo_accCode.getSelectionModel().getSelectedItem();
            int accountCode = account.getAccountCode();

            String vendorPN = txt_vendorPartNo.getText();

            Vendor vendor = combo_vendor.getSelectionModel().getSelectedItem();
            int vendorId = vendor.getVendorId();

            String partNoun = txt_partNoun.getText();
            String description = txt_description.getText();
            Double cost = Double.parseDouble(txt_cost.getText());
            int minLvl = Integer.parseInt(txt_min.getText());
            int maxLvl = Integer.parseInt(txt_max.getText());

            Location location = combo_location.getSelectionModel().getSelectedItem();
            String locationId = location.getLocationId();

            if ((vendorPN.isEmpty() || partNoun.isEmpty() || description.isEmpty()) ||
                    combo_location.getSelectionModel().isEmpty() || combo_vendor.getSelectionModel().isEmpty() ||
                    combo_location.getSelectionModel().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Information",
                        "Please complete all fields.");
            } else if (!isInt(txt_max) || !isInt(txt_min) || !isDub(txt_cost)) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Information",
                        "Min and Max should be a whole number using characters 0-9. Cost Should be A Decimal Number " +
                                "such as '200.98'. ");
            } else if (Part.dupeFound(vendorPN, vendorId)) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Dupe Found",
                        "A Part with this number is already provided by this vendor");
            } else {
                Part part = new Part(accountCode, vendorPN, vendorId, partNoun, description, minLvl, maxLvl, cost, locationId);

                String partNumber = dbm.generateUniquePartNo(part);
                part.setPartNumber(partNumber);

                dbm.createPart(part);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/PartMaster.fxml"));
                Stage partsStage = new Stage();
                partsStage.setTitle("RICS 1.0 Part Master");
                partsStage.initStyle(StageStyle.TRANSPARENT);
                partsStage.setScene(new Scene(loader.load()));
                PartMasterController controller = loader.getController();
                controller.initData(part);

                partsStage.show();
                closeAddPart();
            }
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Information",
                    "Please complete all fields correctly.");
            e.printStackTrace();
        }

    }

    /**
     * Input validation for numeric fields
     *
     * @param num - textfield being validated
     * @return boolean success value
     */
    private boolean isInt(JFXTextField num) {
        try {
            Integer.parseInt(num.getText());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Input validation for cost field
     *
     * @param num textfield being validated
     * @return boolean success value
     */
    private boolean isDub(JFXTextField num) {
        try {
            Double.parseDouble(num.getText());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Closes AddPart.fxml
     */
    @FXML
    private void closeAddPart() {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

}
