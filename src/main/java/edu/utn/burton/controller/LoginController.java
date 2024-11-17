/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.auth.GoogleAuthStrategy;
import edu.utn.burton.auth.LoginStrategy;
import edu.utn.burton.auth.PlatziAuthStrategy;
import edu.utn.burton.entities.Message;
import edu.utn.burton.handlers.APIHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

/**
 *
 * @author alexledezma
 */
public class LoginController implements Initializable {

    @FXML
    private CustomTextField Correo;
    @FXML
    private CustomPasswordField Contraseña;
    @FXML
    private MFXButton Ingresar;    
    @FXML 
    private MFXButton googleBtn;

    APIHandler api = new APIHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Ingresar.setOnAction(ev -> {
            auth();
        });
        
        googleBtn.setOnAction(ev -> {
            googleAuth();
        });
    }

    private void auth() {
        PlatziAuthStrategy platziAuth = new PlatziAuthStrategy();
        if (platziAuth.auth(Correo.getText(), Contraseña.getText())) {
            loadMenu();
        } else {
            Alerts.show(new Message("Error de autenticación", "Email o Contraseña incorrectos."), Alert.AlertType.WARNING);
        }
    }
    
    
    
    private void googleAuth(){
        GoogleAuthStrategy googleAuth = new GoogleAuthStrategy();
        if(googleAuth.oAuth()){
            loadMenu();
        } else {
            Alerts.show(new Message("Error de autenticación", "Ha ocurrido un error durante la autenticación con Google."), Alert.AlertType.WARNING);
        }
    }

    private void loadMenu() {
        MenuController.initGui(new Stage());
        Stage stage = (Stage) Ingresar.getScene().getWindow();
        stage.close();
    }

    public static void initGui() {

        try {
            FXMLLoader loader = new FXMLLoader(Burton.class.getResource("/fxml/Login.fxml"));

            LoginController controller = new LoginController();
            loader.setController(controller);

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Burton");
            stage.setResizable(false);
            stage.getIcons().add(new Image(Burton.class.getResourceAsStream("/assets/icon.png")));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
