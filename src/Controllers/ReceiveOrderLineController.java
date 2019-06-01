package Controllers;

import Models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Controls Actions for Receiving OrderLines
 */
public class ReceiveOrderLineController
{
    @FXML
    private Label lbl_orderNo;

    @FXML
    private Label lbl_orderLineId;

    @FXML
    private Label lbl_partNumber;

    @FXML
    private Label lbl_noun;

    @FXML
    private Label lbl_qty;

    @FXML
    private JFXTextField txt_recQty;

    @FXML
    private JFXTextField txt_manifest;

    @FXML
    private JFXButton btn_receive;

    @FXML
    private JFXButton btn_cancel;

    /**
     * Sets the Labels with orderLine info
     * @param orderNumber
     * @param orderLineId
     */
    @FXML
    public void setLabels(String orderNumber, int orderLineId)
    {
        try
        {
            OrderLine ol = OrderLine.returnOrderLine(orderLineId, orderNumber);
            lbl_orderNo.setText(orderNumber);
            lbl_orderLineId.setText(String.valueOf(orderLineId));
            lbl_partNumber.setText(ol.getPart().getPartNumber());
            lbl_noun.setText(ol.getPart().getPartNoun());
            lbl_qty.setText(String.valueOf(ol.getQuantity()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns to OrdersMenu.fxml
     * @throws IOException
     */
    @FXML
    private void on_cancelClick() throws IOException
    {
        Order o = Order.returnOrder(lbl_orderNo.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/OrdersMenu.fxml"));
        Stage ordersMenu = new Stage(StageStyle.TRANSPARENT);
        ordersMenu.setTitle("RICS 1.0 Orders Menu");
        ordersMenu.setScene(new Scene(loader.load()));

        OrdersMenuController controller = loader.getController();
        controller.initData(o);
        ordersMenu.show();
        closeRecOrderLine();
    }

    /**
     * Updates the Received Quantity on the orderLine given the number is valid
     */
    @FXML
    private void on_recClick()
    {
        try
        {
            Window window = btn_cancel.getScene().getWindow();
            DBManager dbm = new DBManager();
            OrderLine ol = OrderLine.returnOrderLine(Integer.parseInt(lbl_orderLineId.getText()), lbl_orderNo.getText());
            Order o = Order.returnOrder(lbl_orderNo.getText());
            Part p = Part.returnPart(ol.getPart().getPartNumber());

            int rec = Integer.parseInt(txt_recQty.getText()) - ol.getReceivedQty();
            if(Integer.parseInt(txt_recQty.getText()) <= ol.getQuantity() && Integer.parseInt(txt_recQty.getText()) > ol.getReceivedQty())
            {
                if(Integer.parseInt(txt_recQty.getText()) == ol.getQuantity())
                {
                    ol.setStatus('C');
                }

                ol.setManifestId(txt_manifest.getText());
                ol.setReceivedQty(Integer.parseInt(txt_recQty.getText()));
                dbm.updateOrderLine(ol, lbl_orderNo.getText());
                dbm.saveTransaction(p,'R', rec, ol.getManifestId());
                dbm.updateStockLevel(p.getOnHand()+rec, p.getPartNumber());
                p.setOnOrder(p.getOnOrder() - rec);
                dbm.updatePart(p);


                on_cancelClick();
            }
            else
            {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Receipt Error", "You have selected " +
                         "an invalid quantity");
                return;
            }


            if(orderClosed())
            {
                o.setOrderStatus('C');
                dbm.updateOrder(o);
            }


            return;

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        closeRecOrderLine();

    }

    /**
     * Checks if the order is Closed
     * @return boolean success value
     */
    private boolean orderClosed()
    {
        DBManager dbm = new DBManager();
        ObservableList<OrderLine> orderLines = dbm.loadOrderLines(lbl_orderNo.getText());
        for (OrderLine orderLine : orderLines) {
            if (orderLine.getStatus() != 'C') {
                return false;
            }
        }
        return true;
    }

    /**
     * Closes the Receive OrderLine Dialog
     */
    @FXML
    private void closeRecOrderLine()
    {
        Stage stage = (Stage)btn_receive.getScene().getWindow();
        stage.close();
    }
}
