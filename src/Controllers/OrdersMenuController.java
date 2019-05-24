package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Order;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class OrdersMenuController implements Initializable
{
    @FXML
    private ImageView btn_home;


    @FXML
    private ImageView btn_newOrder;

    @FXML
    private ImageView btn_update;

    @FXML
    private ImageView btn_newLine;

    @FXML
    private ImageView btn_approve;

    @FXML
    private ImageView btn_cancel;

    @FXML
    private ImageView btn_print;

    @FXML
    private ImageView btn_removeItem;

    @FXML
    private Label lbl_orderNo;

    @FXML
    private JFXTextField txt_date;

    @FXML
    private JFXComboBox combo_shipping;

    @FXML
    private JFXComboBox combo_orderType;

    @FXML
    private JFXTextField txt_status;

    @FXML
    private JFXTextArea txt_header;

    @FXML
    private TableView tbl_order;

    @FXML
    private TableColumn col_orderNo;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        txt_header.setEditable(false);
        initData();
        txt_date.setEditable(false);
        combo_shipping.setDisable(true);
        combo_orderType.setDisable(true);
        txt_status.setEditable(false);



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
            closeOrdersMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_newOrderClick()
    {
        Date date = new Date();
        txt_date.setText(String.valueOf(date));
        combo_shipping.setDisable(false);
        combo_orderType.setDisable(false);
        txt_header.setEditable(true);


        try
        {
            DBManager dbm = new DBManager();
            String orderNumber = dbm.generateUniqueOrderNo();

            lbl_orderNo.setText(orderNumber);
            combo_shipping.getSelectionModel().selectFirst();
            String shippingMethod = String.valueOf(combo_shipping.getSelectionModel().getSelectedItem());
            combo_orderType.getSelectionModel().selectFirst();
            char orderType = String.valueOf(combo_orderType.getSelectionModel().getSelectedItem()).charAt(0);
            txt_status.setText("U");
            txt_header.setText("");

            //(String orderNumber, char orderType, String shippingMethod,String header)
            Order order = new Order(orderNumber, orderType, shippingMethod);

            dbm.addOrder(order);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            refreshTable();
        }
    }

    @FXML
    private void on_cancelClick()
    {
        closeOrdersMenu();
    }

    private void closeOrdersMenu()
    {
        Stage stage = (Stage)btn_home.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void on_updateClick()
    {
        Window window = btn_cancel.getScene().getWindow();
        DBManager dbm = new DBManager();
        ObservableList<Order> ordersOBS = dbm.loadOrders();
        String orderNumber = lbl_orderNo.getText();
        Order order = dbm.returnOrder(ordersOBS, orderNumber);

        if(!order.getOrderApproved())
        {
            order.setShippingMethod(String.valueOf(combo_shipping.getSelectionModel().getSelectedItem()));
            order.setOrderType(String.valueOf(combo_orderType.getSelectionModel().getSelectedItem()).charAt(0));
            order.setHeader(txt_header.getText());
            dbm.updateOrder(order);
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Order updated", "You have successfully " +
                    "update order " + order.getOrderNumber());
            refresh(order);
            return;
        }
        else
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Unable to update", "Approved orders " +
                    "cannot be edited");
            return;
        }
    }


    public void initData()
    {
        try
        {
            DBManager dbm = new DBManager();
            ObservableList<Order> ordersOBS = dbm.loadOrders();

            tbl_order.setItems(ordersOBS);
            col_orderNo.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
            ObservableList < String > shipMethods = FXCollections.observableArrayList();

            shipMethods.add(0, "OCEAN");
            shipMethods.add(1, "AIR");
            shipMethods.add(2, "ROAD");
            shipMethods.add(3, "RAIL");

            ObservableList<String> orderType = FXCollections.observableArrayList();

            orderType.add(0, "PURCHASE ORDER");
            orderType.add(1, "REPAIR ORDER");
            orderType.add(2, "SERVICE REQUISITION ORDER");

            combo_shipping.setItems(shipMethods);
            combo_orderType.setItems(orderType);

            tbl_order.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = tbl_order.getSelectionModel().getSelectedIndex();
                    Order order = (Order) tbl_order.getItems().get(index);


                    int varOT = getVarOT(order);


                    lbl_orderNo.setText(order.getOrderNumber());
                    txt_date.setText(String.valueOf(order.getDate()));
                    combo_shipping.getSelectionModel().select(order.getShippingMethod());
                    combo_orderType.getSelectionModel().select(varOT);
                    txt_status.setText(String.valueOf(order.getOrderStatus()));
                    txt_header.setText(order.getHeader());
                    if(!order.getOrderApproved())
                    {
                        txt_header.setEditable(true);
                        combo_orderType.setDisable(false);
                        combo_shipping.setDisable(false);
                    }



                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void refreshTable()
    {
        DBManager dbm = new DBManager();
        ObservableList<Order> ordersOBS = dbm.loadOrders();
        tbl_order.setItems(ordersOBS);
        col_orderNo.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
    }

    public void refresh(Order order)
    {
        refreshTable();

        int varOT;

        varOT = getVarOT(order);

        txt_date.setText(String.valueOf(order.getDate()));
        combo_shipping.getSelectionModel().select(order.getShippingMethod());
        combo_orderType.getSelectionModel().select(varOT);
        txt_status.setText(String.valueOf(order.getOrderStatus()));
        txt_header.setText(order.getHeader());
    }

    private int getVarOT(Order order) {
        int varOT;
        switch (order.getOrderType())
        {
            case 'P':
                varOT =0;
                break;
            case 'R':
                varOT =1;
                break;
            case 'S':
                varOT =2;
                break;
            default:
                varOT =0;
                break;
        }
        return varOT;
    }
}
