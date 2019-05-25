package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Location;
import Models.Part;
import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePartController implements Initializable
{
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

    @FXML
    private JFXButton btn_save;


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    @FXML
    public void setLabel(String partNo)
    {
        lbl_part.setText(partNo);
    }

    @FXML
    protected void initData(String p)
    {
        try
        {
            DBManager dbm = new DBManager();
            ObservableList<Part> parts = dbm.loadParts();
            Part part = dbm.returnPart(parts, p);
            txt_partNoun.setText(part.getPartNoun());
            txt_description.setText(part.getDescription());
            txt_cost.setText(String.valueOf(part.getUnitCost()));
            txt_min.setText(String.valueOf(part.getMinRecVal()));
            txt_max.setText(String.valueOf(part.getMaxRecVal()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_saveClick() throws IOException
    {
        String partNo = lbl_part.getText();
        String partNoun = txt_partNoun.getText();
        String description = txt_description.getText();
        Double cost = Double.parseDouble(txt_cost.getText());
        int min =Integer.parseInt(txt_min.getText());
        int max =Integer.parseInt(txt_min.getText());
        Location location = combo_location.getSelectionModel().getSelectedItem();
        String locationId = location.getLocationId();

        Window window = btn_cancel.getScene().getWindow();

        if(partNoun.isEmpty() ||description.isEmpty() || txt_cost.getText().equals("") || txt_min.getText().equals("") ||  txt_max.getText().equals("")  || locationId.isEmpty())
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing Information", "please ensure all fields " +
                    "are completed");

            return;
        }
        else if(min > max || min < 0)
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Invalid Information", "please ensure the min is " +
                    "a valid number and less than the max");

            return;
        }
        else
        {
            try
            {
                DBManager dbm = new DBManager();
                ObservableList<Part> partsOBS = dbm.loadParts();

                for(Part part : partsOBS)
                {
                    if(part.getPartNumber().equals(partNo))
                    {
                        //accountCode, vendorPN, vendorId, partNoun, description, minLvl, maxLvl, cost, locationI
                        Part uPart = new Part(partNo, partNoun, description, min, max, cost, locationId);
                        dbm.updatePart(uPart);
                        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Part Updated", "you have " +
                                "successfully updated the stock card for " + partNo + ".");
                        return;
                    }
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void on_cancelClick()
    {
        closeUpdatePart();
    }

    private void closeUpdatePart()
    {
        Stage stage = (Stage)btn_save.getScene().getWindow();
        stage.close();
    }


}
