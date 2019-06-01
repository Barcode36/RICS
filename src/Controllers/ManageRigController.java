package Controllers;

import Models.AlertHelper;
import Models.DBManager;
import Models.Rig;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles Actions for Adding & Updating Rig Files
 */
public class ManageRigController implements Initializable
{
    @FXML
    private JFXButton btn_cancel;

    @FXML
    private JFXTextField txt_rigNo;

    @FXML
    private JFXTextField txt_rigName;

    @FXML
    private JFXTextField txt_clientName;

    @FXML
    private JFXTextField txt_wellName;

    @FXML
    private TableView tbl_rigs;

    @FXML
    private TableColumn col_rigName;

    /**
     * Initialises the rigs table on ManageRig.fxml
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<Rig> rigsOBS = dbm.loadRigs();

        tbl_rigs.setItems(rigsOBS);
        col_rigName.setCellValueFactory(new PropertyValueFactory<>("rigName"));

        try
        {
            tbl_rigs.setOnMouseClicked((MouseEvent event) ->
            {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    int index = tbl_rigs.getSelectionModel().getSelectedIndex();
                    Rig rig = (Rig) tbl_rigs.getItems().get(index);

                    txt_rigNo.setText(Integer.toString(rig.getRigNo()));
                    txt_rigName.setText(rig.getRigName());
                    txt_clientName.setText(rig.getClientName());
                    txt_wellName.setText(rig.getWellName());
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }


    /**
     * Adds new Rig to DB Rigs Table or Updates Rig if RigNo is already registered
     */
    @FXML
    private void on_saveClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<Rig> rigsOBS = dbm.loadRigs();
        Window window = btn_cancel.getScene().getWindow();

        int rigNo = Integer.parseInt(txt_rigNo.getText());
        String rigName = txt_rigName.getText();
        String clientName = txt_clientName.getText();
        String wellName = txt_wellName.getText();

        if(rigName.isEmpty() || clientName.isEmpty() || wellName.isEmpty() || txt_rigNo.getText().isEmpty() ||
                !isString(txt_clientName)|| !isString(txt_rigName) || !isString(txt_wellName))
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Inavlid information", "Please check your  " +
                    "inputs.");
            return;
        }
        else if(Rig.containsRig(rigsOBS, rigNo))
        {
            try
            {
                Rig rig = new Rig(rigNo, rigName, clientName, wellName);
                dbm.updateRig(rig);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information updated", "The record for Rig " +
                        rigNo + "has been updated. ");
                return;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        else
            {

                try {
                    Rig rig = new Rig(rigNo, rigName, clientName, wellName);
                    dbm.createRig(rig);

                    AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Rig added", "you have " +
                            "successfully created added a new rig record");
                    return;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                {
                    refresh();
                }
            }
    }

    /**
     * Clears the text fields for addign a new record
     */
    @FXML
    private void on_addClick()
    {
        txt_rigNo.setText("");
        txt_rigName.setText("");
        txt_clientName.setText("");
        txt_wellName.setText("");
    }

    /**
     * Closes  ManageRig.fxml
     */
    @FXML
    private void closeAddRig()
    {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Refreshes the Rigs Table after change
     */
    @FXML
    private void refresh() {
        DBManager dbm = new DBManager();
        ObservableList<Rig> rigsOBS = dbm.loadRigs();

        //populate table view
        tbl_rigs.setItems(rigsOBS);

        col_rigName.setCellValueFactory(new PropertyValueFactory<>("rigName"));

        txt_rigNo.setText("");
        txt_rigName.setText("");
        txt_clientName.setText("");
        txt_wellName.setText("");
    }

    /**
     * Checks the field is not a number
     * @param input textfield to be checked
     * @return boolean success value
     */
    private boolean isString(JFXTextField input)
    {
        try{
            Integer.parseInt(input.getText());
           return false;
        }catch (Exception e)
        {
            e.printStackTrace();
           return true;
        }
    }

}
