package Controllers;


import Models.AlertHelper;
import Models.DBManager;
import Models.InventoryAccount;
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
 * Handles Actions for Managing Inventory Accounts (View, Add, Update)
 */
public class InventoryAccountsController implements Initializable
{

    @FXML
    private JFXTextField txt_accountCode;

    @FXML
    private JFXTextField txt_accountName;

    @FXML
    private TableView tbl_accounts;

    @FXML
    private TableColumn col_accountCode;

    /**
     * Initialises The Accounts Table & Fills TextFields on selection from Table
     * @param location
     * @param resources
     */
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

    /**
     * Controls Actions for adding + updating InventoryAccounts
     */
    @FXML
    private void on_saveClick()
    {
        DBManager dbm = new DBManager();
        ObservableList<InventoryAccount> accountsOBS = dbm.loadInventoryAccounts();
        Window window = tbl_accounts.getScene().getWindow();

        int accountCode = 0;
        try {
            accountCode = Integer.parseInt(txt_accountCode.getText());
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Invalid information", "Please provide a valid " +
                    " account code");
            return;
        }

        String accountName = txt_accountName.getText();

        if (accountName.isEmpty() || !isFormat(txt_accountCode))
        {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Invalid information", "Please provide a valid " +
                    "account name");
            return;
        }

        else if(InventoryAccount.containsAccount(accountsOBS, accountCode))
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
                dbm.createInventoryAccount(account);

                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Account added", "you have " +
                        "successfully created a new Inventory Account");
                return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Unable to Add", "The Account was " +
                        "not added to the system.");
                return;

            }
            finally
            {
                refresh();
            }
        }
    }

    /**
     * Input validation for accountCode field
     * @param num textfield being validated
     * @return boolean success value
     */
    private boolean isFormat(JFXTextField num)
    {
        try
        {
            Integer.parseInt(num.getText());
            return num.getText().length() == 7;
        }catch (Exception e)
        {
            e.printStackTrace();
            return  false;
        }
    }

    /**
     *Clears Text Fields
     */
    @FXML
    private void on_addClick()
    {
        txt_accountCode.setText("");
        txt_accountName.setText("");
    }

    /**
     * Closes InventoryAccounts.fxml
     */
    @FXML
    private void closeInventoryAccounts()
    {
        Stage stage = (Stage) tbl_accounts.getScene().getWindow();
        stage.close();
    }

    /**
     * Refreshes The InventoryAccounts Table and Text Fields
     */
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
