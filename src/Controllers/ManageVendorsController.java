package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Vendor;
import com.jfoenix.controls.JFXButton;
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

public class ManageVendorsController implements Initializable
{
    @FXML
    private JFXButton btn_cancel;

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

    @FXML
    private void on_saveClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Vendor> vendors = dbm.loadVendors();
        Window window = btn_cancel.getScene().getWindow();

        int vendorId = Integer.parseInt(txt_vendorId.getText());
        String vendorName = txt_vendorName.getText();
        String phoneNumber = txt_phoneNumber.getText();
        String address = txt_address.getText();

        if(vendorName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || txt_vendorId.getText().equals(""))
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing information", "Please complete all " +
                    "fields");
            return;
        }
        else if(Vendor.containsVendor(vendors, vendorId))
        {
            try
            {
                Vendor vendor = new Vendor(vendorId, vendorName, phoneNumber, address);
                dbm.updateVendor(vendor);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information updated", "The record for " +
                        vendorName + " has been updated. ");
                return;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        else
            {

                try {
                    Vendor vendor = new Vendor(vendorId, vendorName, phoneNumber, address);
                    dbm.createVendor(vendor);

                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Vendor added", "you have " +
                            "successfully created added a new vendor record");
                    return;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                {
                    refresh();
                }
            }
    }




    @FXML
    private void on_addClick()
    {
        txt_vendorId.setText("");
        txt_vendorName.setText("");
        txt_phoneNumber.setText("");
        txt_address.setText("");
    }

    @FXML
    private void closeAddVendor()
    {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();
    }

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