package Models;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable
{


    @FXML
    private Label lbl_welcome;

    @FXML
    private AnchorPane root;


    public static AnchorPane rootP;


    private String username;

    public void initialize (URL url, ResourceBundle rb)
    {

        lbl_welcome.setText("Welcome " + username);

        if(!Main.isSplashLoaded)
        {
            loadSplashScreen();

        }


        //lbl_welcome.setText("Welcome "+ lbl_username);

        rootP = root;
    }

    public void setLabel(String uname)
    {
        try
        {
            username = uname;
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }


    @FXML
    private void loadSplashScreen()
    {
        Main.isSplashLoaded = true;
        try
        {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Splash.fxml"));
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
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("../Models/LandingPage.fxml")));

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



}
