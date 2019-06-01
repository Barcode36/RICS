package Controllers;

import Models.DBManager;
import Models.InventoryAccount;
import Models.OrderLine;
import Models.Part;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportsMenuController implements Initializable
{
    @FXML
    private JFXButton btn_generate;

    @FXML
    private JFXComboBox  combo_account;

    @FXML
    private JFXRadioButton rdo_acc;

    /**
     * Closes Reports Menu.fxml
     */
    @FXML
    private void closeReportsMenu()
    {
        Stage stage = (Stage)btn_generate.getScene().getWindow();
        stage.close();
    }

    /**
     * returns to LandingPage.fxml
     */
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

    /**
     * Generates a Current Open Order & OverMax Stock Report for ALl Inventory or Selected Account
     */
    @FXML
    private void generateStockReport()
    {
        try
        {
            DBManager dbm = new DBManager();
            String selection = "7";
            if (!rdo_acc.isSelected())
            {
                selection = String.valueOf(combo_account.getSelectionModel().getSelectedItem()).substring(0, 3);
            }
            ObservableList<OrderLine> openOrders = dbm.openOrders(selection);
            ObservableList<Part> parts = dbm.loadParts();


            double total = 0;
            double excess = 0;
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(new File("StockReport.pdf")));
            document.open();

            Image logo = Image.getInstance("ddlogo.png");
            Paragraph title = new Paragraph("DRILL-DOWN Stock Report", FontFactory.getFont(FontFactory.COURIER_BOLD, 18));
            document.add(new Paragraph("   "));
            document.add(new Paragraph("   "));
            title.setAlignment(1);
            logo.setAlignment(1);
            document.add(logo);
            document.add(title);
            document.add(new Paragraph("   "));
            document.add(new Paragraph("   "));

            PdfPTable table = new PdfPTable(6);
            PdfPCell cell = new PdfPCell(new Paragraph("Open Orders"));
            cell.setColspan(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            table.addCell("Order No");
            table.addCell("Qty");
            table.addCell("Rec");
            table.addCell("Part Number");
            table.addCell("Manifest");
            table.addCell("Line Total");

            for (OrderLine orderLine : openOrders) {
                String orderNumber = orderLine.getOrderNumber();
                String quan = String.valueOf(orderLine.getQuantity());
                String partNum = orderLine.getPart().getPartNumber();
                String manID = orderLine.getManifestId();
                String rec = String.valueOf(orderLine.getReceivedQty());
                String lineTotal = String.valueOf(orderLine.getLineTotal());

                table.addCell(orderNumber);
                table.addCell(quan);
                table.addCell(rec);
                table.addCell(partNum);
                table.addCell(manID);
                table.addCell(lineTotal);

                total = total + orderLine.getLineTotal();
            }
            document.add(table);
            document.add(new Paragraph("Total Cost of Open Orders: £" + total));

            document.add(new Paragraph("   "));
            document.add(new Paragraph("   "));

            PdfPTable omTable = new PdfPTable(10);
            PdfPCell omCell = new PdfPCell(new Paragraph("Over Max Parts"));
            omCell.setColspan(10);
            omCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            omCell.setBackgroundColor(Color.RED);
            omTable.addCell(omCell);


            omTable.addCell("Part No.");
            PdfPCell descCell = new PdfPCell(new Paragraph("Description"));
            descCell.setColspan(2);
            omTable.addCell(descCell);
            PdfPCell nounCell = new PdfPCell(new Paragraph("Part Noun"));
            nounCell.setColspan(2);
            omTable.addCell(nounCell);
            omTable.addCell("Location");
            omTable.addCell("On Order");
            omTable.addCell("Min");
            omTable.addCell("Max");
            omTable.addCell("On Hand");

            for (Part part : parts)
            {
                if(part.getOnHand() > part.getMaxRecVal())
                {
                    String partNumber = part.getPartNumber();
                    String desc = part.getDescription();
                    String noun = part.getPartNoun();
                    String location = part.getLocation();
                    String oo = String.valueOf(part.getOnHand());
                    String min = String.valueOf(part.getMinRecVal());
                    String max = String.valueOf(part.getMaxRecVal());
                    String oh = String.valueOf(part.getOnHand());


                    omTable.addCell(partNumber);
                    PdfPCell descVCell = new PdfPCell(new Paragraph(desc));
                    descVCell.setColspan(2);
                    omTable.addCell(descVCell);
                    PdfPCell nounVCell = new PdfPCell(new Paragraph(noun));
                    nounVCell.setColspan(2);
                    omTable.addCell(nounVCell);
                    omTable.addCell(location);
                    omTable.addCell(oo);
                    omTable.addCell(min);
                    omTable.addCell(max);
                    omTable.addCell(oh);

                    excess = excess +(part.getOnHand() - part.getMaxRecVal()) * part.getUnitCost();

                }

            }
            document.add(omTable);
            document.add(new Paragraph("Excess inventory: £" + excess));
            document.close();


            Desktop.getDesktop().open(new File("C:\\Users\\David\\Documents\\2nd " +
                    "Year\\InventoryControlSystem\\StockReport.pdf"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    /**
     * Initialises the Inventory Accounts comboBox
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<InventoryAccount> accounts = dbm.loadInventoryAccounts();
        combo_account.setItems(accounts);
    }
}

