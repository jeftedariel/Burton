/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.User;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.handlers.APIHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    private void auth() {
        JSONObject credenciales = new JSONObject();
        credenciales.put("email", Correo.getText());
        credenciales.put("password", Contraseña.getText());

        try {
            JSONObject user = api.post(User.class, "auth/login", credenciales);
            if (user.has("access_token")) {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+user.getString("access_token"));
                
                User usr = api.getObject(User.class, "auth/profile",headers );
                
                UserSession.getInstance().login(usr.id(), usr.name(), usr.email(), usr.avatar(), usr.role());
                loadMenu();
                System.out.println(UserSession.getInstance().toString());
            } else {
                Alerts.show(new Message("Error de autenticación", "Email o Contraseña incorrectos."), Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);

        }
    }
    

    private void loadMenu() {
        MenuController.initGui();
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
