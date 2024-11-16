/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private MFXButton store;

    @FXML
    private ImageView avatar;

    @FXML
    private Text username;

    @FXML
    private MFXButton logout;

    @FXML
    private MFXButton trendingSellsReport;

    @FXML
    private MFXButton comparativeReport;

    @FXML
    private MFXButton turnoverReport;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        store.setOnMouseClicked(ev -> {
            MenuController.initGui((Stage) store.getScene().getWindow());
        });

        logout.setOnMouseClicked(ev -> {
            LoginController.logout(store);
        });
        
        trendingSellsReport.setOnMouseClicked(ev->{
        });
        

        loadUserInfo();

    }

    public void loadUserInfo() {
        username.setText(UserSession.getInstance().getName());
        BufferedImage image;

        try {
            image = ImageIO.read(new URL(UserSession.getInstance().getAvatar()));
            avatar.setImage(SwingFXUtils.toFXImage(image, null));
        } catch (Exception ee) {
            System.out.println("Hubo un error al intentar cargar la img");
        }
    }

    public static void initGui(Stage stage) {
        try {

            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/Dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Burton.class.getResource("/styles/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
