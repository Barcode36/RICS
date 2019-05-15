package Controllers;

import Models.DBManager;
import Models.Part;
import Models.Vendor;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class PartMasterController implements Initializable
{
    @FXML
    private TableView tbl_parts;

    @FXML
    private TableColumn col_partNo;

    @FXML
    private Label lbl_partNo;

    @FXML
    private JFXTextField txt_vendorPartNo;

    @FXML
    private JFXTextField txt_accountCode;

    @FXML
    private JFXTextField txt_partNoun;

    @FXML
    private JFXTextField txt_vendor;

    @FXML
    private JFXTextField txt_location;

    @FXML
    private JFXTextField txt_lastOrder;

    @FXML
    private JFXTextField txt_unitCost;

    @FXML
    private JFXTextField txt_unitOfMeasure;

    @FXML
    private Label lbl_onHand;

    @FXML
    private Label lbl_min;

    @FXML
    private Label lbl_max;

    @FXML
    private Label lbl_flagged;

    @FXML
    private JFXTextArea txt_description;

    @FXML
    private ImageView btn_home;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<Part> partsOBS = dbm.loadParts();
        ObservableList<Vendor> vendorsOBS = dbm.loadVendors();

        //populate table view
        tbl_parts.setItems(partsOBS);

        col_partNo.setCellValueFactory(new PropertyValueFactory<>("partNumber"));

        try {
            tbl_parts.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = tbl_parts.getSelectionModel().getSelectedIndex();
                    Part part = (Part) tbl_parts.getItems().get(index);

                    lbl_partNo.setText(part.getPartNumber());
                    txt_vendorPartNo.setText(part.getVendorNumber());
                    txt_accountCode.setText(Integer.toString(part.getAccountCode()));
                    txt_partNoun.setText(part.getPartNoun());
                    txt_location.setText(part.getLocation());
                    txt_lastOrder.setText(part.getLastOrder());
                    txt_unitCost.setText(Double.toString(part.getUnitCost()));
                    lbl_min.setText("Min : " + part.getMinRecVal());
                    lbl_max.setText("Max : " + part.getMaxRecVal());
                    lbl_onHand.setText(" OH: " + part.getOnHand());
                    lbl_flagged.setText("  F : " + part.getFlagged());
                    txt_description.setText(part.getDescription());
                    txt_unitOfMeasure.setText(part.getUnitOfMeasure());
                    if(vendorsOBS.contains(part.getVendorId()))
                    {
                        Vendor vendor = new Vendor();
                        vendor.setVendorId(part.getVendorId());

                        txt_vendor.setText(vendor.toString());
                    }

                    //lbl_onOrder
                    //FIX VENDOR TO DISPLAY AS VENDOR NAME


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
            closePartMaster();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void closePartMaster()
    {
        Stage stage = (Stage)btn_home.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void on_addPartClick()
    {
        try
        {
            Stage addPartStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../Views/AddPart.fxml"));
            Scene scene = new Scene(root);
            addPartStage.setScene(scene);
            addPartStage.setTitle("RICS 1.0 New Part");
            addPartStage.initStyle(StageStyle.TRANSPARENT);
            addPartStage.show();
            closePartMaster();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
