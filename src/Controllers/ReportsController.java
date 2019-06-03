package Controllers;

import Models.*;
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
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportsController implements Initializable
{
    @FXML
    private JFXButton btn_generate;

    @FXML
    private ImageView btn_close;

    @FXML
    private JFXComboBox  combo_account;

    @FXML
    private JFXRadioButton rdo_acc;

    /**
     * Closes Reports.fxml returns to landing page
     */
    @FXML
    private void closeReportsMenu() throws IOException {
        Stage homeStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/LandingPage.fxml"));
        Scene scene = new Scene(root);
        homeStage.setScene(scene);
        homeStage.setTitle("RICS 1.0 Home");
        homeStage.initStyle(StageStyle.TRANSPARENT);
        homeStage.show();
        Stage stage = (Stage) btn_generate.getScene().getWindow();
        stage.close();
    }

    /**
     * Generates a Current Open Order & OverMax Stock Report for ALl Inventory or Selected Account
     */
    @FXML
    private void generateStockReport() {
        try {
            Window window = btn_close.getScene().getWindow();
            DBManager dbm = new DBManager();
            String selection = "7";
            if (!rdo_acc.isSelected() && combo_account.getSelectionModel().isEmpty()) {

                AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing Information", "please select an " +
                        "option");
                return;
            } else {
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

                for (Part part : parts) {
                    if (part.getOnHand() > part.getMaxRecVal()) {
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

                        excess = excess + (part.getOnHand() - part.getMaxRecVal()) * part.getUnitCost();


                    }

                }
                document.add(omTable);
                document.add(new Paragraph("Excess inventory: £" + excess));

                document.add(new Paragraph("   "));
                document.add(new Paragraph("   "));

                PdfPTable flaggedTable = new PdfPTable(11);
                PdfPCell flagCell = new PdfPCell(new Paragraph("Flagged Parts"));
                flagCell.setColspan(11);
                flagCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                flagCell.setBackgroundColor(Color.BLUE);
                flaggedTable.addCell(flagCell);

                flaggedTable.addCell("Part No.");
                PdfPCell desc = new PdfPCell(new Paragraph("Description"));
                desc.setColspan(2);
                flaggedTable.addCell(desc);
                PdfPCell noun = new PdfPCell(new Paragraph("Part Noun"));
                noun.setColspan(2);
                flaggedTable.addCell(noun);
                flaggedTable.addCell("Location");
                flaggedTable.addCell("On Order");
                flaggedTable.addCell("Min");
                flaggedTable.addCell("Max");
                flaggedTable.addCell("On Hand");
                flaggedTable.addCell("Flagged");

                ObservableList<Part> fParts = Part.flaggedParts();

                for (Part part : fParts) {
                    String partNumber = part.getPartNumber();
                    String des = part.getDescription();
                    String pNoun = part.getPartNoun();
                    String location = part.getLocation();
                    String oo = String.valueOf(part.getOnHand());
                    String min = String.valueOf(part.getMinRecVal());
                    String max = String.valueOf(part.getMaxRecVal());
                    String oh = String.valueOf(part.getOnHand());
                    String flag = String.valueOf(part.getFlagged());

                    flaggedTable.addCell(partNumber);
                    PdfPCell desVCell = new PdfPCell(new Paragraph(des));
                    desVCell.setColspan(2);
                    flaggedTable.addCell(desVCell);
                    PdfPCell pNounVCell = new PdfPCell(new Paragraph(pNoun));
                    pNounVCell.setColspan(2);
                    flaggedTable.addCell(pNounVCell);
                    flaggedTable.addCell(location);
                    flaggedTable.addCell(oo);
                    flaggedTable.addCell(min);
                    flaggedTable.addCell(max);
                    flaggedTable.addCell(oh);
                    flaggedTable.addCell(flag);

                }
                document.add(new Paragraph(" Flagged Items for all accounts are listed."));
                document.add(new Paragraph("   "));
                document.add(new Paragraph("   "));
                document.add(flaggedTable);
                document.close();


                Desktop.getDesktop().open(new File("C:\\Users\\David\\Documents\\2nd " +
                        "Year\\InventoryControlSystem\\StockReport.pdf"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Clears selection from components
     */
    @FXML
    private void on_clearClick() {
        rdo_acc.setSelected(false);
        combo_account.getSelectionModel().clearSelection();
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

