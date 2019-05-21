package Controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable
{

    @FXML
    private Label lbl_welcome;

    @FXML
    private ImageView btn_users;


    @FXML
    private AnchorPane root;


    public static AnchorPane rootP;



    public void initialize (URL url, ResourceBundle rb)
    {


       /* if(!Main.isSplashLoaded)
        {
            loadSplashScreen();

        }*/


        rootP = root;
    }



    @FXML
    private void loadSplashScreen()
    {
        Main.isSplashLoaded = true;
        try
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../Views/Splash.fxml"));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e)->
            {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e)->
            {
                try
                {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("../Views/LandingPage.fxml")));

                    root.getChildren().setAll(parentContent);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void on_usersClick() throws IOException
    {

        Stage usersStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/usersMenu.fxml"));
        Scene scene = new Scene(root);
        usersStage.setScene(scene);
        usersStage.setTitle("RICS 1.0 User Account Management");
        usersStage.initStyle(StageStyle.TRANSPARENT);
        usersStage.show();
        closeLandingPage();
    }

    @FXML
    private void on_partsClick() throws IOException
    {

        Stage partsStage = new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("../Views/PartMaster.fxml"));
        Scene scene1 = new Scene(root1);
        partsStage.setScene(scene1);
        partsStage.setTitle("RICS 1.0 Part Master");
        partsStage.initStyle(StageStyle.TRANSPARENT);
        partsStage.show();
        closeLandingPage();
    }

    @FXML
    private void on_ordersClick() throws IOException
    {
        Stage ordersStage = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("../Views/OrdersMenu.fxml"));
        Scene scene2 = new Scene(root2);
        ordersStage.setScene(scene2);
        ordersStage.setTitle("RICS 1.0 Orders");
        ordersStage.initStyle(StageStyle.TRANSPARENT);
        ordersStage.show();
        closeLandingPage();
    }

    @FXML
    private void on_manifestsClick() throws IOException
    {
        Stage manifestStage = new Stage();
        Parent root3 = FXMLLoader.load(getClass().getResource("../Views/manifestMenu.fxml"));
        Scene scene3 = new Scene(root3);
        manifestStage.setScene(scene3);
        manifestStage.setTitle("RICS 1.0 Manifests");
        manifestStage.initStyle(StageStyle.TRANSPARENT);
        manifestStage.show();
        closeLandingPage();
    }

    @FXML
    private void on_reportsClick() throws IOException
    {
        Stage reportsStage = new Stage();
        Parent root4 = FXMLLoader.load(getClass().getResource("../Views/ReportsMenu.fxml"));
        Scene scene4 = new Scene(root4);
        reportsStage.setScene(scene4);
        reportsStage.setTitle("RICS 1.0 Reports");
        reportsStage.initStyle(StageStyle.TRANSPARENT);
        reportsStage.show();
        closeLandingPage();
    }

    @FXML
    private void on_locationClick() throws IOException
    {
        Stage locationStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/AddLocation.fxml"));
        Scene scene = new Scene(root);
        locationStage.setScene(scene);
        locationStage.setTitle("RICS 1.0 Add a new storage location");
        locationStage.initStyle(StageStyle.TRANSPARENT);
        locationStage.show();
    }

    @FXML
    private void on_rigClick() throws IOException
    {
        Stage rigStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/ManageRig.fxml"));
        Scene scene = new Scene(root);
        rigStage.setScene(scene);
        rigStage.setTitle("RICS 1.0 Add a rig");
        rigStage.initStyle(StageStyle.TRANSPARENT);
        rigStage.show();
    }

    private void closeLandingPage() {
        Stage lstage = (Stage)btn_users.getScene().getWindow();
        lstage.close();
    }



}
