package Controllers;


import Models.AlertHelper;
import Models.DBManager;
import Models.InventoryAccount;
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

public class InventoryAccountsController implements Initializable
{
    @FXML
    private JFXButton btn_cancel;

    @FXML
    private JFXTextField txt_accountCode;

    @FXML
    private JFXTextField txt_accountName;


    @FXML
    private TableView tbl_accounts;

    @FXML
    private TableColumn col_accountCode;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        DBManager dbm = new DBManager();
        ObservableList<InventoryAccount> accountsOBS = dbm.loadInventoryAccounts();

        tbl_accounts.setItems(accountsOBS);
        col_accountCode.setCellValueFactory(new PropertyValueFactory<>("accountCode"));

        try
        {
            tbl_accounts.setOnMouseClicked((MouseEvent event) ->
            {
                if (event.getButton().equals(MouseButton.PRIMARY))
                {
                    int index = tbl_accounts.getSelectionModel().getSelectedIndex();
                    InventoryAccount account = (InventoryAccount) tbl_accounts.getItems().get(index);

                    txt_accountCode.setText(Integer.toString(account.getAccountCode()));
                    txt_accountName.setText(account.getAccountName());
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }

    @FXML
    private void on_saveClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<InventoryAccount> accountsOBS = dbm.loadInventoryAccounts();
        Window window = btn_cancel.getScene().getWindow();

        int accountCode = Integer.parseInt(txt_accountCode.getText());
        String accountName = txt_accountName.getText();

        if(accountName.isEmpty() || txt_accountCode.getText().equals(""))
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Missing information", "Please complete all " +
                    "fields");
            return;
        }
        else if(DBManager.containsAccount(accountsOBS, accountCode))
        {
            try
            {
                InventoryAccount account = new InventoryAccount(accountCode, accountName);
                dbm.updateInventoryAccount(account);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Information updated", "Account name for " +
                        accountCode + "has been updated. ");
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
                InventoryAccount account = new InventoryAccount(accountCode, accountName);
                dbm.addInventoryAccount(account);

                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Account added", "you have " +
                        "successfully created a new Inventory Account");
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


    @FXML
    private void on_cancelClick()
    {
        closeInventoryAccounts();
    }


    @FXML
    private void on_addClick()
    {
        txt_accountCode.setText("");
        txt_accountName.setText("");
    }

    private void closeInventoryAccounts()
    {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void refresh() {
        DBManager dbm = new DBManager();
        ObservableList<InventoryAccount> accountsOBS = dbm.loadInventoryAccounts();

        //populate table view
        tbl_accounts.setItems(accountsOBS);

        col_accountCode.setCellValueFactory(new PropertyValueFactory<>("accountCode"));

        txt_accountCode.setText("");
        txt_accountName.setText("");
    }

}
