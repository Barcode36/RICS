package Controllers;

import Models.DBManager;
import Models.User;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private JFXTextField txt_shipping;

    @FXML
    private JFXTextField txt_approved;

    @FXML
    private JFXTextField txt_status;

    @FXML
    private JFXTextArea txt_header;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        txt_date.setEditable(false);
        txt_shipping.setEditable(false);
        txt_approved.setEditable(false);
        txt_status.setEditable(false);
        txt_header.setEditable(false);

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
        txt_date.setEditable(true);
        txt_shipping.setEditable(true);
        txt_approved.setEditable(true);
        txt_status.setEditable(true);
        txt_header.setEditable(true);

        try
        {
            DBManager dbm = new DBManager();
            String orderNumber = dbm.generateUniqueOrderNo();
            Date date = new Date();

            lbl_orderNo.setText(orderNumber);
            txt_date.setText("");
            txt_shipping.setText("");
            txt_approved.setText("U");
            txt_date.setText(String.valueOf(date));
            txt_header.setText("");
        }
        catch(Exception e)
        {
            e.printStackTrace();
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


}
