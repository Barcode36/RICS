package Controllers;

import Models.DBManager;
import Models.Order;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class ReportsMenuController
{
    @FXML
    private JFXButton btn_generate;

    @FXML
    private JFXComboBox<String> combo_report;

    @FXML
    private void closeReportsMenu()
    {
        Stage stage = (Stage)btn_generate.getScene().getWindow();
        stage.close();
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
            closeReportsMenu();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    private void on_generateCLick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Order> orders = dbm.openOrders();
    }
//    @FXML
//    private void on_generateClick() throws IOException, DocumentException
//    {
//
//        DBManager dbm = new DBManager();
//        String type = combo_report.getSelectionModel().getSelectedItem();
//
//        switch(type)
//        {
//            case "Flagged Items":
//                ObservableList<Part> flaggedParts = Part.flaggedParts();
//                break;
//            case "Open Orders":
//                ObservableList<Order> openOrders = dbm.openOrders();
//
//        }
//
//
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream(new File(type + "Report.pdf")));
//        document.open();
//
//        Image logo = Image.getInstance("ddlogo.png");
//        Paragraph title = new Paragraph("DRILL-DOWN " + type, FontFactory.getFont(FontFactory.COURIER_BOLD, 18));
//        document.add(new Paragraph("   "));
//        document.add(new Paragraph("   "));
//        title.setAlignment(1);
//        logo.setAlignment(1);
//        document.add(logo);
//        document.add(title);
//        if(order.getOrderStatus() == 'U')
//        {
//            Paragraph alert = new Paragraph("ORDER UNAPPROVED");
//            document.add(alert);
//        }
//        document.add(new Paragraph(String.valueOf(order.getDate())));
//        document.add(new Paragraph(lbl_orderNo.getText(), FontFactory.getFont(FontFactory.COURIER_BOLD, 16)));
//        document.add(new Paragraph("Shipping Method: " + order.getShippingMethod()));
//        document.add(new Paragraph("Header:"));
//        document.add(new Paragraph(txt_header.getText()));
//        document.add(new Paragraph("   "));
//
//        PdfPTable table = new PdfPTable(6);
//        PdfPCell cell = new PdfPCell(new Paragraph("Order Lines"));
//        cell.setColspan(6);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(cell);
//
//
//        table.addCell("Status");
//        table.addCell("Qty");
//        table.addCell("Rec");
//        table.addCell("Part Number");
//        table.addCell("Manifest");
//        table.addCell("Line Total");
//
//        for (OrderLine orderLine : orderLines)
//        {
//            String status = String.valueOf(orderLine.getStatus());
//            String quan = String.valueOf(orderLine.getQuantity());
//            String partNum = orderLine.getPart().getPartNumber();
//            String manID = orderLine.getManifestId();
//            String rec = String.valueOf(orderLine.getQuantity());
//            String lineTotal = String.valueOf(orderLine.getLineTotal());
//
//            table.addCell(status);
//            table.addCell(quan);
//            table.addCell(rec);
//            table.addCell(partNum);
//            table.addCell(manID);
//            table.addCell(lineTotal);
//        }
//        document.add(table);
//
//        document.add(new Paragraph("Order Total: £" + order.getOrderTotal()));
//        document.close();
//
//
//        Desktop.getDesktop().open(new File("C:\\Users\\David\\Documents\\2nd Year\\InventoryControlSystem\\Order"+order.getOrderNumber()+".pdf"));
//    }

}

