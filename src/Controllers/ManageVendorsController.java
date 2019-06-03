package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Vendor;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles Actions for Adding and Updating Vendors in DB Vendors Table
 */
public class ManageVendorsController implements Initializable
{

    @FXML
    private JFXTextField txt_vendorId;

    @FXML
    private JFXTextField txt_vendorName;

    @FXML
    private JFXTextField txt_phoneNumber;

    @FXML
    private JFXTextField txt_address;

    @FXML
    private TableView tbl_vendors;

    @FXML
    private TableColumn col_vendorName;

    /**
     * Initialises the Vendors Table
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<Vendor> vendors = dbm.loadVendors();

        tbl_vendors.setItems(vendors);
        col_vendorName.setCellValueFactory(new PropertyValueFactory<>("vendorName"));

        try
        {
            tbl_vendors.setOnMouseClicked((MouseEvent event) ->
            {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    int index = tbl_vendors.getSelectionModel().getSelectedIndex();
                    Vendor vendor = (Vendor) tbl_vendors.getItems().get(index);

                    txt_vendorId.setText(Integer.toString(vendor.getVendorId()));
                    txt_vendorName.setText(vendor.getVendorName());
                    txt_phoneNumber.setText(vendor.getPhoneNumber());
                    txt_address.setText(vendor.getShippingAddress());
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }

    /**
     * Updates or Adds Vendor Record in DB Vendors Table
     */
    @FXML
    private void on_saveClick() {
        try {
            DBManager dbm = new DBManager();
            ObservableList<Vendor> vendors = dbm.loadVendors();
            Window window = tbl_vendors.getScene().getWindow();

            int vendorId = Integer.parseInt(txt_vendorId.getText());
            String vendorName = txt_vendorName.getText();
            String phoneNumber = txt_phoneNumber.getText();
            String address = txt_address.getText();

            if (vendorName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || txt_vendorId.getText().isEmpty() ||
                    !isString(txt_address) || !isString(txt_vendorName)) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing information", "Please complete all " +
                        "fields");
            } else if (Vendor.containsVendor(vendors, vendorId)) {

                Vendor vendor = new Vendor(vendorId, vendorName, phoneNumber, address);
                dbm.updateVendor(vendor);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information updated", "The record for " +
                        vendorName + " has been updated. ");
            } else
                {


                Vendor vendor = new Vendor(vendorId, vendorName, phoneNumber, address);
                dbm.createVendor(vendor);

                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Vendor added", "you have " +
                        "successfully created added a new vendor record");


                refresh();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies textfield is not a number
     * @param input
     * @return
     */
    private boolean isString(JFXTextField input)
    {
        try{
            Integer.parseInt(input.getText());
            return false;
        }catch (Exception e)
        {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Clears textfields for adding new record
     */
    @FXML
    private void on_addClick()
    {
        txt_vendorId.setText("");
        txt_vendorName.setText("");
        txt_phoneNumber.setText("");
        txt_address.setText("");
    }

    /**
     * Closes ManageVendors.fxml
     */
    @FXML
    private void closeAddVendor()
    {
        Stage stage = (Stage) tbl_vendors.getScene().getWindow();
        stage.close();
    }

    /**
     * refreshes the Vendors Table
     */
    @FXML
    private void refresh()
    {
        DBManager dbm = new DBManager();
        ObservableList<Vendor> vendors = dbm.loadVendors();

        //populate table view
        tbl_vendors.setItems(vendors);

        col_vendorName.setCellValueFactory(new PropertyValueFactory<>("vendorName"));

        txt_vendorId.setText("");
        txt_vendorName.setText("");
        txt_phoneNumber.setText("");
        txt_address.setText("");
    }

}
