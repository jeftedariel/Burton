/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.User;
import edu.utn.burton.handlers.APIHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 *
 * @author alexledezma
 */
public class LoginController implements Initializable {

    @FXML
    private MFXTextField Correo;
    @FXML
    private MFXPasswordField Contraseña;
    @FXML
    private MFXButton Ingresar;
    
    APIHandler api = new APIHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Ingresar.setOnAction(ev -> {
            auth();
        });
    }

    public void auth() {
    JSONObject credenciales = new JSONObject();
    credenciales.put("email", Correo.getText());
    credenciales.put("password", Contraseña.getText());

    try {
        List<User> users = api.post(User.class, "auth/login", credenciales);
        if (users != null && !users.isEmpty()) {
            MenuController.initGui();
            Stage stage = (Stage) Ingresar.getScene().getWindow();
            stage.close();
        }
    } catch (Exception e) {
        System.out.println("Error: " + e);
        
    }
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
