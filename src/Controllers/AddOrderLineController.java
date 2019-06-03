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

/**
 * AddOrderLine allows Admin Users to add new OrderLines to unapproved Orders
 */
public class AddOrderLineController implements Initializable
{
    @FXML
    private TableView tbl_parts;

    @FXML
    private TableColumn col_partNumber;

    @FXML
    private TableColumn col_description;

    @FXML
    private JFXTextField txt_partNo, txt_filter;

    @FXML
    private JFXTextField txt_requestedBy;

    @FXML
    private JFXTextField txt_qty;

    @FXML
    private Label lbl_orderNo;

    @FXML
    private JFXButton btn_clear;

    /**
     * Initalises the Parts Table and fills fields with selected Part Info
     * @param location
     * @param resources
     */
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

    /**
     * AddOrderLine Controller Adds the new OrderLine to Order / Inserts OrderLine to DB OrderLines Table
     */
    @FXML
    private void on_addClick()
    {

        Window window = btn_clear.getScene().getWindow();

        if(txt_partNo.getText().isEmpty() || txt_requestedBy.getText().isEmpty() || txt_qty.getText().isEmpty())
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Information",
                    "Please complete all fields.");
        }
        else if(!isInt(txt_qty))
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Information",
                    "Quantity must be a whole number using characters 0-9");
        }
        else {
            try {
                DBManager dbm = new DBManager();

                String orderNumber = lbl_orderNo.getText();
                int orderLineId = dbm.generateUniqueOrderLineId(orderNumber);
                int qty = Integer.parseInt(txt_qty.getText());
                Part part = Part.returnPart(txt_partNo.getText());
                String ref = txt_requestedBy.getText();
                Order order = Order.returnOrder(orderNumber);
                OrderLine orderLine = new OrderLine(orderLineId, qty, part, ref);
                dbm.addOrderLine(orderLine, orderNumber);
                order.calculateOrderTotal();


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

                controller.initData(order);


            } catch (Exception e) {
                e.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Information",
                        "Error: Unable to add to order ");
            }
        }

    }

    /**
     * Input validation for quantity field
     * @param txt_qty quantity of part being added to orderLine
     * @return
     */
    private boolean isInt(JFXTextField txt_qty)
    {
        try
        {
            Integer.parseInt(txt_qty.getText());
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return  false;
        }
    }
    /**
     * Initialises the Order Number Label
     * @param orderNumber - Order the orderline is being added to
     */
    public void initData(String orderNumber)
    {
        lbl_orderNo.setText(orderNumber);
    }


    /**
     * Clears the text fields of any input
     */
    @FXML
    private void on_clearClick()
    {
        txt_partNo.setText("");
        txt_qty.setText("");
        txt_requestedBy.setText("");
    }

    /**
     * Filters the Parts Table to Parts matching the users criteria
     * Checks the criteria against PartNumber, Description, VendorPartNumber, Location
     */
    @FXML
    private void on_filterClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Part> partsOBS = dbm.basicSearchParts(txt_filter.getText());

        tbl_parts.setItems(partsOBS);

        col_partNumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }


    /**
     * Closes addOrderLine.fxml
     */
    @FXML
    private void closeAddOrderLine()
    {
        Stage stage = (Stage)btn_clear.getScene().getWindow();
        stage.close();
    }

}
