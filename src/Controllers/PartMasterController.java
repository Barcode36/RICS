package Controllers;

import Models.*;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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

/**
 * PartMaster Controller allows Users to browse and Manage Inventory
 */
public class PartMasterController implements Initializable {
    @FXML
    private TableView tbl_parts;

    @FXML
    private TableColumn col_partNumber;

    @FXML
    private TableView tbl_history;

    @FXML
    private TableColumn col_transType;

    @FXML
    private TableColumn col_transDate;

    @FXML
    private TableColumn col_partNo;

    @FXML
    private TableColumn col_reference;

    @FXML
    private TableColumn col_qty;

    @FXML
    private TableColumn col_cost;

    @FXML
    private TableColumn col_totalVal;

    @FXML
    private Label lbl_partNo;

    @FXML
    private JFXTextField txt_vendorPartNo;

    @FXML
    private JFXTextField txt_accountCode;

    @FXML
    private JFXTextField txt_partNoun;

    @FXML
    private JFXTextField txt_vendor;

    @FXML
    private JFXTextField txt_location;

    @FXML
    private JFXTextField txt_lastOrder;

    @FXML
    private JFXTextField txt_unitCost;

    @FXML
    private JFXTextField txt_unitOfMeasure;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private Label lbl_onHand;

    @FXML
    private Label lbl_min;

    @FXML
    private Label lbl_max;

    @FXML
    private Label lbl_onOrder;

    @FXML
    private Label lbl_flagged;

    @FXML
    private JFXTextArea txt_description;

    @FXML
    private ImageView btn_home;

    @FXML
    private Label lbl_add, lbl_update, lbl_delete, lbl_issue;

    @FXML
    private ImageView btn_addPart, btn_edit, btn_delete, btn_issue;


    /**
     * Initialises the Parts Table
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBManager dbm = new DBManager();
        ObservableList<Part> partsOBS = dbm.loadParts();
        initData(partsOBS.get(0));

        try {
            tbl_parts.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = tbl_parts.getSelectionModel().getSelectedIndex();
                    Part part = (Part) tbl_parts.getItems().get(index);

                    initData(part);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Navigates to LandingPage.fxml
     */
    @FXML
    private void on_homeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/LandingPage.fxml"));
            Stage homeStage = new Stage();
            homeStage.setTitle("RICS 1.0 Landing Page");
            homeStage.initStyle(StageStyle.TRANSPARENT);
            homeStage.setScene(new Scene(loader.load()));
            LandingPageController controller = loader.getController();
            controller.initData(Main.user);
            homeStage.show();
            closePartMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes current part from DB Parts Table
     */
    @FXML
    private void on_deleteClick() {
        try {
            DBManager dbm = new DBManager();
            ObservableList<Part> parts = dbm.loadParts();
            Part part = Part.returnPart(lbl_partNo.getText());

            dbm.deletePart(part);
            initData(parts.get(0));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Add new Part Dialog
     */
    @FXML
    private void on_addPartClick() {
        try {
            Stage addPartStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../Views/AddPart.fxml"));
            Scene scene = new Scene(root);
            addPartStage.setScene(scene);
            addPartStage.setTitle("RICS 1.0 New Part");
            addPartStage.initStyle(StageStyle.TRANSPARENT);
            addPartStage.show();
            closePartMaster();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the Issue Part Dialog
     *
     * @throws IOException
     */
    @FXML
    private void on_issueClick() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/IssuePart.fxml"));
        Stage issueStage = new Stage();
        issueStage.setTitle("RICS 1.0 Issue Part");
        issueStage.initStyle(StageStyle.TRANSPARENT);
        issueStage.setScene(new Scene(loader.load()));
        IssuePartController controller = loader.getController();
        controller.setLabel(lbl_partNo.getText());
        issueStage.show();
        closePartMaster();

    }

    /**
     * Allows users to edit some details of parts in inventory
     *
     * @throws IOException
     */
    @FXML
    private void on_editClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/UpdatePart.fxml"));
        Stage updateStage = new Stage();
        updateStage.setTitle("RICS 1.0 Update Part");
        updateStage.initStyle(StageStyle.TRANSPARENT);
        updateStage.setScene(new Scene(loader.load()));
        UpdatePartController controller = loader.getController();
        updateStage.show();
        controller.setLabel(lbl_partNo.getText());
        controller.initData(lbl_partNo.getText());
        closePartMaster();

    }


    /**
     * Generates a PDF Stock Card for The Part with a list of transactions
     *
     * @throws IOException
     * @throws DocumentException
     */
    @FXML
    private void on_printClick() throws IOException, DocumentException {

        DBManager dbm = new DBManager();
        Part part = Part.returnPart(lbl_partNo.getText());
        ObservableList<Transaction> transactions = dbm.loadTransactions(part);

        Window window = btn_home.getScene().getWindow();
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(new File("stockCard" + part.getPartNumber() + ".pdf")));
        document.open();

        Image logo = Image.getInstance("ddlogo.png");
        Paragraph title = new Paragraph("DRILL-DOWN STOCK CARD", FontFactory.getFont(FontFactory.COURIER_BOLD, 18));
        document.add(new Paragraph(" --------------  "));
        document.add(new Paragraph("   "));
        title.setAlignment(1);
        logo.setAlignment(1);
        document.add(logo);
        document.add(title);
        document.add(new Paragraph(new Date().toString()));
        document.add(new Paragraph("Part Number : " + lbl_partNo.getText()));
        document.add(new Paragraph("Vendor Part Number : " + txt_vendor.getText()));
        document.add(new Paragraph("Inventory Account : " + txt_accountCode.getText()));
        document.add(new Paragraph("Part Noun : " + txt_partNoun.getText()));
        document.add(new Paragraph("Location : " + txt_location.getText()));
        document.add(new Paragraph("Last Order : " + txt_lastOrder.getText()));
        document.add(new Paragraph("Unit Cost (£) : " + txt_unitCost.getText()));
        document.add(new Paragraph("Unit of Measure : " + txt_unitOfMeasure.getText()));
        document.add(new Paragraph(lbl_min.getText()));
        document.add(new Paragraph(lbl_max.getText()));
        document.add(new Paragraph(lbl_onOrder.getText()));
        document.add(new Paragraph(lbl_onHand.getText()));
        document.add(new Paragraph(lbl_flagged.getText()));
        document.add(new Paragraph("Description : "));
        document.add(new Paragraph(txt_description.getText()));
        document.add(new Paragraph("   "));

        PdfPTable table = new PdfPTable(7);
        PdfPCell cell = new PdfPCell(new Paragraph("Transaction History"));
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);


        table.addCell("Type");
        table.addCell("Date");
        table.addCell("Part #");
        table.addCell("Reference");
        table.addCell("Qty");
        table.addCell("Cost");
        table.addCell("Total Value");

        for (Transaction transaction : transactions) {
            String type = String.valueOf(transaction.getTransType());
            String date = String.valueOf(transaction.getTransDate());
            String partNo = transaction.getPartNo();
            String ref = transaction.getReference();
            String qty = String.valueOf(transaction.getQuantity());
            String cost = String.valueOf(transaction.getPrice());
            String val = String.valueOf(transaction.getTotalVal());

            table.addCell(type);
            table.addCell(date);
            table.addCell(partNo);
            table.addCell(ref);
            table.addCell(qty);
            table.addCell(cost);
            table.addCell(val);
        }
        document.add(table);
        document.close();

        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Stock card generated", "stock card for " +
                lbl_partNo.getText() + " has been generated.");

        Desktop.getDesktop().open(new File("C:\\Users\\David\\Documents\\2nd Year\\InventoryControlSystem\\stockCard" + part.getPartNumber() + ".pdf"));
    }

    /**
     * Initialises the data in the Parts Table, Transactions Table and the Part Information fields
     *
     * @param part
     */
    @FXML
    protected void initData(Part part) {
        DBManager dbm = new DBManager();
        ObservableList<Vendor> vendors = dbm.loadVendors();
        ObservableList<Part> partsOBS = dbm.loadParts();


        //populate table view
        tbl_parts.setItems(partsOBS);

        col_partNumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));

        lbl_partNo.setText(part.getPartNumber());
        txt_vendorPartNo.setText(part.getVendorNumber());
        txt_accountCode.setText(Integer.toString(part.getAccountCode()));
        txt_partNoun.setText(part.getPartNoun());
        txt_location.setText(part.getLocation());
        txt_lastOrder.setText(part.getLastOrder());
        txt_unitCost.setText(Double.toString(part.getUnitCost()));
        lbl_min.setText("Min : " + part.getMinRecVal());
        lbl_max.setText("Max : " + part.getMaxRecVal());
        lbl_onHand.setText(" OH: " + part.getOnHand());
        lbl_onOrder.setText(" OO: " + part.getOnOrder());
        lbl_flagged.setText("  F : " + part.getFlagged());
        txt_description.setText(part.getDescription());
        txt_unitOfMeasure.setText(part.getUnitOfMeasure());
        for (Vendor vendor : vendors) {
            if (vendor.getVendorId() == part.getVendorId()) {
                txt_vendor.setText(vendor.toString());
            }
        }

        ObservableList<Transaction> sTransactions = dbm.loadTransactions(part);
        tbl_history.setItems(sTransactions);
        col_transType.setCellValueFactory(new PropertyValueFactory<>("transType"));
        col_transDate.setCellValueFactory(new PropertyValueFactory<>("transDate"));
        col_partNo.setCellValueFactory(new PropertyValueFactory<>("partNo"));
        col_reference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_cost.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_totalVal.setCellValueFactory(new PropertyValueFactory<>("totalVal"));

        if (!Main.user.getAdminUser()) {
            lbl_add.setVisible(false);
            lbl_update.setVisible(false);
            lbl_issue.setVisible(false);
            lbl_delete.setVisible(false);
            btn_addPart.setVisible(false);
            btn_edit.setVisible(false);
            btn_issue.setVisible(false);
            btn_delete.setVisible(false);
        }
    }


    /**
     * Closes the Application
     */
    @FXML
    protected void closePartMaster() {
        Stage stage = (Stage) btn_home.getScene().getWindow();
        stage.close();
    }

    /**
     * Searches the DB Parts Table Columns partNumber, Location, vendorsPartNumber, partNoun, description for matches
     * to the users criteria
     */
    @FXML
    private void on_searchClick() {
        DBManager dbm = new DBManager();
        ObservableList<Part> partsOBS = dbm.basicSearchParts(txt_search.getText());

        tbl_parts.setItems(partsOBS);

        col_partNumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
    }


}
