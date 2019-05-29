package Controllers;

import Models.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class OrdersMenuController implements Initializable
{
    @FXML
    private ImageView btn_home;


    @FXML
    private ImageView btn_cancel, btn_search;


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
    private JFXTextField txt_total, txt_search;


    @FXML
    private JFXTextArea txt_header;

    @FXML
    private TableView tbl_order;

    @FXML
    private TableView tbl_orderLines;

    @FXML
    private TableColumn col_orderNo;

    @FXML
    private TableColumn col_status;

    @FXML
    private TableColumn col_qty;

    @FXML
    private TableColumn col_rec;

    @FXML
    private TableColumn col_partNo;

    @FXML
    private TableColumn col_manifestId;


    @FXML
    private TableColumn col_lineTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<Order> ordersOBS = dbm.loadOrders();

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

        try
        {
        //populate table view
        tbl_order.setItems(ordersOBS);
        col_orderNo.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

        Order o = ordersOBS.get(0);
        initData(o);



        ObservableList<OrderLine> orderLines = dbm.loadOrderLines(o.getOrderNumber());
        tbl_orderLines.setItems(orderLines);
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_rec.setCellValueFactory(new PropertyValueFactory<>("receivedQty"));
        col_partNo.setCellValueFactory(new PropertyValueFactory<>("part"));
        col_manifestId.setCellValueFactory(new PropertyValueFactory<>("manifestId"));
        col_lineTotal.setCellValueFactory(new PropertyValueFactory<>("lineTotal"));



            tbl_order.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    int index = tbl_order.getSelectionModel().getSelectedIndex();
                    Order order = (Order) tbl_order.getItems().get(index);

                    initData(order);

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
            closeOrdersMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_addLIClick() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AddOrderLine.fxml"));
        Stage orderLineStage = new Stage();
        orderLineStage.setTitle("RICS 1.0 Add a line item");
        orderLineStage.initStyle(StageStyle.TRANSPARENT);
        orderLineStage.setScene(new Scene(loader.load()));
        AddOrderLineController controller = loader.getController();
        controller.setLabel(lbl_orderNo.getText());
        orderLineStage.show();
        closeOrdersMenu();
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
        try
        {
        Window window = btn_cancel.getScene().getWindow();
        DBManager dbm = new DBManager();
        String orderNumber = lbl_orderNo.getText();

        Order order = Order.returnOrder(orderNumber);

        if(order.getOrderApproved())
        {
            dbm.cancelOrder(order);
            order.calculateOrderTotal();
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Order cancelled", "Please inform " +
                    "the purchasing dept.");
            initData(order);
            return;
        }else{
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Unapproved order", "Unapproved orders " +
                    "can be re-purposed.");
            return;
        }
    }
        catch(Exception e)
    {
        e.printStackTrace();
    }
    }

    @FXML
    private void on_approveClick()
    {

        try
        {
            Window window = btn_cancel.getScene().getWindow();
            DBManager dbm = new DBManager();
            String orderNumber = lbl_orderNo.getText();

            Order order = Order.returnOrder(orderNumber);
            ObservableList<OrderLine> ol = dbm.loadOrderLines(orderNumber);
            if(ol.size() > 0) {
                dbm.approveOrder(order);
                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Order Approved", "Export your approved " +
                        "orders to the Purchasing Dept at the end of the day.");

                for(OrderLine orderLine : ol)
                {
                    Part p =  orderLine.getPart();
                    p.setLastOrder(orderNumber);
                    p.setOnOrder(p.getOnOrder() + orderLine.getQuantity());
                    if(p.getFlagged() >= orderLine.getQuantity())
                    {
                        p.setFlagged(p.getFlagged() - orderLine.getQuantity());
                    }
                    dbm.updatePart(p);
                    dbm.saveTransaction(p, 'O', orderLine.getQuantity(), orderLine.getRequestedBy());
                    on_generateClick();
                }
                initData(order);
                return;
            }else
                {
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Not Approved", "Add something " +
                        "to the order before approving it.");

                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_updateClick()
    {
        Window window = btn_cancel.getScene().getWindow();
        DBManager dbm = new DBManager();
        ObservableList<Order> ordersOBS = dbm.loadOrders();
        String orderNumber = lbl_orderNo.getText();
        Order order = Order.returnOrder(orderNumber);

        if(!order.getOrderApproved())
        {
            order.setShippingMethod(String.valueOf(combo_shipping.getSelectionModel().getSelectedItem()));
            order.setOrderType(String.valueOf(combo_orderType.getSelectionModel().getSelectedItem()).charAt(0));
            order.setHeader(txt_header.getText());
            dbm.updateOrder(order);
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Order updated", "You have successfully " +
                    "update order " + order.getOrderNumber());
            initData(order);
            return;
        }
        else
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Unable to update", "Approved orders " +
                    "cannot be edited");
            return;
        }
    }


    @FXML
    private void on_removeClick()
    {
        Window window = btn_cancel.getScene().getWindow();
        try {
            DBManager dbm = new DBManager();
            String orderNumber = lbl_orderNo.getText();
            Order order = Order.returnOrder(orderNumber);
            OrderLine orderLine = (OrderLine) tbl_orderLines.getSelectionModel().getSelectedItem();

            char status = orderLine.getStatus();
            switch (status) {
                case 'U':
                    if (dbm.removeOrderLine(orderLine, orderNumber)) {
                        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Order updated", "You have successfully " +
                                "removed line item " + orderLine.getOrderLineId());
                        order.calculateOrderTotal();
                        initData(order);
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "There was a problem removing " +
                                "the orderLine. ");
                    }
                    break;
                case 'X':
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "The orderLine has already been " +
                            "cancelled");
                    break;
                case 'O':
                    orderLine.setLineTotal(0);
                    orderLine.setStatus('X');
                    dbm.updateOrderLine(orderLine, orderNumber);
                    Part p = orderLine.getPart();
                    p.setOnOrder(p.getOnOrder()- orderLine.getQuantity());
                    p.setFlagged(p.getFlagged() + orderLine.getQuantity());
                    dbm.updatePart(p);
                    order.calculateOrderTotal();
                    if(orderCancelled())
                    {
                        order.setOrderStatus('X');
                        dbm.updateOrder(order);
                    }
                    initData(order);
                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Order updated", "You have successfully " +
                            "cancelled line item " + orderLine.getOrderLineId());
                    break;
                case 'C':
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "The orderLine has already been " +
                            "received.");
                    break;
            }
        }
        catch(Exception e)
            {
                e.printStackTrace();
            }

    }


    @FXML
    private void on_generateClick() throws IOException, DocumentException {


        DBManager dbm = new DBManager();
        Order order = Order.returnOrder(lbl_orderNo.getText());
        ObservableList<OrderLine> orderLines = dbm.loadOrderLines(order.getOrderNumber());

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(new File("Order"+order.getOrderNumber()+".pdf")));
        document.open();

        String type="";
        switch(order.getOrderType())
        {
            case 'P':
                type = "Purchase Order";
                break;
            case 'R':
                type = "Repair Order";
                break;
            case 'S':
                type = "Service Order";
                break;

        }

        Image logo = Image.getInstance("ddlogo.png");
        Paragraph title = new Paragraph("DRILL-DOWN " + type , FontFactory.getFont(FontFactory.COURIER_BOLD, 18));
        document.add(new Paragraph("   "));
        document.add(new Paragraph("   "));
        title.setAlignment(1);
        logo.setAlignment(1);
        document.add(logo);
        document.add(title);
        if(order.getOrderStatus() == 'U')
        {
            Paragraph alert = new Paragraph("ORDER UNAPPROVED");
            document.add(alert);
        }
        document.add(new Paragraph(String.valueOf(order.getDate())));
        document.add(new Paragraph(lbl_orderNo.getText(), FontFactory.getFont(FontFactory.COURIER_BOLD, 16)));
        document.add(new Paragraph("Shipping Method: " + order.getShippingMethod()));
        document.add(new Paragraph("Header:"));
        document.add(new Paragraph(txt_header.getText()));
        document.add(new Paragraph("   "));

        PdfPTable table = new PdfPTable(6);
        PdfPCell cell = new PdfPCell(new Paragraph("Order Lines"));
        cell.setColspan(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);


        table.addCell("Status");
        table.addCell("Qty");
        table.addCell("Rec");
        table.addCell("Part Number");
        table.addCell("Manifest");
        table.addCell("Line Total");

        for (OrderLine orderLine : orderLines)
        {
            String status = String.valueOf(orderLine.getStatus());
            String quan = String.valueOf(orderLine.getQuantity());
            String partNum = orderLine.getPart().getPartNumber();
            String manID = orderLine.getManifestId();
            String rec = String.valueOf(orderLine.getQuantity());
            String lineTotal = String.valueOf(orderLine.getLineTotal());

            table.addCell(status);
            table.addCell(quan);
            table.addCell(rec);
            table.addCell(partNum);
            table.addCell(manID);
            table.addCell(lineTotal);
        }
        document.add(table);

        document.add(new Paragraph("Order Total: £" + order.getOrderTotal()));
        document.close();


        Desktop.getDesktop().open(new File("C:\\Users\\David\\Documents\\2nd Year\\InventoryControlSystem\\Order"+order.getOrderNumber()+".pdf"));
    }

    @FXML
    private void on_receiveClick() throws IOException
    {
        Window window = btn_cancel.getScene().getWindow();
        OrderLine orderLine = (OrderLine) tbl_orderLines.getSelectionModel().getSelectedItem();

        if(orderLine.getStatus()=='O')
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/ReceiveOrderLine.fxml"));
            Stage recStage = new Stage();
            recStage.setTitle("RICS 1.0 Receive OrderLine");
            recStage.initStyle(StageStyle.TRANSPARENT);
            recStage.setScene(new Scene(loader.load()));
            ReceiveOrderLineController controller = loader.getController();
            controller.setLabels(lbl_orderNo.getText(), tbl_orderLines.getSelectionModel().getSelectedIndex()+1);
            recStage.show();
            closeOrdersMenu();
        }
        else
        {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "The orderLine cannot be received ");
        }

    }


    @FXML
    private void on_searchClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Order> orders = dbm.searchOrders(txt_search.getText().toUpperCase());

        tbl_order.setItems(orders);

        col_orderNo.setCellValueFactory(new PropertyValueFactory<>("partNumber"));

    }


    protected void initData(Order order)
    {
        try
        {
            DBManager dbm = new DBManager();
            ObservableList<Order> ordersOBS = dbm.loadOrders();


            tbl_order.setItems(ordersOBS);
            col_orderNo.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));


                    int varOT = getVarOT(order);


                    lbl_orderNo.setText(order.getOrderNumber());
                    txt_date.setText(String.valueOf(order.getDate()));
                    combo_shipping.getSelectionModel().select(order.getShippingMethod());
                    combo_orderType.getSelectionModel().select(varOT);
                    txt_status.setText(String.valueOf(order.getOrderStatus()));
                    txt_header.setText(order.getHeader());
                    txt_total.setText(String.valueOf(order.getOrderTotal()));
                    if(!order.getOrderApproved())
                    {
                        txt_header.setEditable(true);
                        combo_orderType.setDisable(false);
                        combo_shipping.setDisable(false);
                    }

                    ObservableList<OrderLine> oLines = dbm.loadOrderLines(order.getOrderNumber());
                    tbl_orderLines.setItems(oLines);
                    col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
                    col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                    col_rec.setCellValueFactory(new PropertyValueFactory<>("receivedQty"));
                    col_partNo.setCellValueFactory(new PropertyValueFactory<>("part"));
                    col_manifestId.setCellValueFactory(new PropertyValueFactory<>("manifestId"));
                    col_lineTotal.setCellValueFactory(new PropertyValueFactory<>("lineTotal"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            tbl_orderLines.setEditable(true);
            if(order.getOrderStatus() == 'O') {
                col_rec.setEditable(true);
            }
        }
    }

    private void refreshTable()
    {
        DBManager dbm = new DBManager();
        ObservableList<Order> ordersOBS = dbm.loadOrders();
        tbl_order.setItems(ordersOBS);
        col_orderNo.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
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

    @FXML
    protected void closeOrdersMenu()
    {
        Stage stage = (Stage)btn_home.getScene().getWindow();
        stage.close();
    }

    private boolean orderCancelled()
    {
        DBManager dbm = new DBManager();
        ObservableList<OrderLine> orderLines = dbm.loadOrderLines(lbl_orderNo.getText());
        for (OrderLine orderLine : orderLines) {
            if (orderLine.getStatus() != 'X') {
                return false;
            }
        }
        return true;
    }

}

