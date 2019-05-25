package Controllers;

import Models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class AddOrderLineController implements Initializable
{
    @FXML
    private TableView tbl_parts;

    @FXML
    private TableColumn col_partNumber;

    @FXML
    private TableColumn col_description;

    @FXML
    private JFXTextField txt_partNo;

    @FXML
    private JFXTextField txt_requestedBy;

    @FXML
    private JFXTextField txt_qty;

    @FXML
    private Label lbl_orderNo;

    @FXML
    private JFXButton btn_clear;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<Part> partsOBS = dbm.loadParts();

        //populate table view
        tbl_parts.setItems(partsOBS);

        col_partNumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            tbl_parts.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    int index = tbl_parts.getSelectionModel().getSelectedIndex();
                    Part part = (Part) tbl_parts.getItems().get(index);

                    txt_partNo.setText(part.getPartNumber());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_addClick()
    {
        Window window = btn_clear.getScene().getWindow();
        try
        {
            DBManager dbm = new DBManager();
            ObservableList<Part> parts = dbm.loadParts();

            String orderNumber = lbl_orderNo.getText();
            int orderLineId = dbm.generateUniqueOrderLineId(orderNumber);
            int qty = Integer.parseInt(txt_qty.getText());
            Part part = dbm.returnPart(parts,txt_partNo.getText());
            String reqBy = txt_requestedBy.getText();


            OrderLine orderLine = new OrderLine(orderLineId, qty, part, reqBy);
            dbm.addOrderLine(orderLine, orderNumber);

            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Item added",
                    qty + " of Part " + part.getPartNumber() + "were added to your order.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/OrdersMenu.fxml"));
            Stage ordersMenu = new Stage(StageStyle.TRANSPARENT);
            ordersMenu.setTitle("RICS 1.0 Orders");
            ordersMenu.setScene(new Scene(loader.load()));

            OrdersMenuController controller = loader.getController();
            controller.closeOrdersMenu();
            ordersMenu.show();
            closeAddOrderLine();


            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Item added",
                    qty + " of Part " + part.getPartNumber() + "were added to your order.");

            controller.initData();

            return;


        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setLabel(String orderNumber)
    {
        lbl_orderNo.setText(orderNumber);
    }

    @FXML
    private void on_clearClick()
    {
        txt_partNo.setText("");
        txt_qty.setText("");
        txt_requestedBy.setText("");
    }


    @FXML
    private void closeAddOrderLine()
    {
        Stage stage = (Stage)btn_clear.getScene().getWindow();
        stage.close();
    }

}
