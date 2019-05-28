package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Part;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class IssuePartController
{

    @FXML
    private JFXTextField txt_quantity;

    @FXML
    private JFXTextField txt_issuedTo;

    @FXML
    private Label lbl_partNo;

    @FXML
    private JFXButton btn_cancel;




    public void setLabel(String partNo)
    {
        try
        {
            lbl_partNo.setText(partNo);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_issueClick()
    {
        int qty = Integer.parseInt(txt_quantity.getText());
        String partNo = lbl_partNo.getText();
        Window window = btn_cancel.getScene().getWindow();

        DBManager dbm = new DBManager();
        Part part = DBManager.returnPart(partNo);
        try
        {
                if (part.getOnHand() >= qty && qty > 0)
                {
                    int newStockLevel = part.getOnHand() - qty;
                    dbm.updateStockLevel(newStockLevel, partNo);
                    dbm.saveTransaction(part, 'I', qty, txt_issuedTo.getText());

                    //flag X amount for order
                    if(newStockLevel < part.getMaxRecVal() && part.getOnHand() > 0)
                    {
                        part.setFlagged(part.getMaxRecVal() - (newStockLevel + part.getOnOrder()));
                    }
                    else if(part.getOnHand() == 0)
                    {
                        part.setFlagged(part.getMaxRecVal());
                    }
                    dbm.updatePart(part);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Partmaster.fxml"));
                    Stage partMaster = new Stage(StageStyle.TRANSPARENT);
                    partMaster.setTitle("RICS 1.0 PartMaster");
                    partMaster.setScene(new Scene(loader.load()));

                   // PartMasterController controller = loader.getController();
                    //controller.closePartMaster();
                    partMaster.show();
                    closeIssuePart();


                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Part issued", "you have " +
                            "issued " + qty + " of " + partNo + ".");

                    return;

                }
                else
                {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Quantity", "There " +
                            "aren't enough of " + partNo + " in stock to issue that amount.");

                    return;
                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void on_cancelClick() throws IOException
    {

        Part p = DBManager.returnPart(lbl_partNo.getText());
        closeIssuePart();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/PartMaster.fxml"));
        Stage ordersMenu = new Stage(StageStyle.TRANSPARENT);
        ordersMenu.setTitle("RICS 1.0 PartMaster");
        ordersMenu.setScene(new Scene(loader.load()));

        PartMasterController controller = loader.getController();
        controller.initData(p);
        ordersMenu.show();
    }


    @FXML
    private void closeIssuePart()
    {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();
    }
}
