/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    Ingresar.setOnAction(ev -> {
            realizarLogin();
        });
        
    }
    
    private void realizarLogin() {
        
    String correo = Correo.getText();
    String contraseña = Contraseña.getText();

    try {
        URL url = new URL("https://api.escuelajs.co/api/v1/auth/login"); //Link al que se hace la consulta
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");//Tipo de consulta al API
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        //Crear el JSON con los datos del usuario
        JSONObject credenciales = new JSONObject();
        credenciales.put("email", correo);  
        credenciales.put("password", contraseña);

        //Se envia la solicitud como un JSON
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = credenciales.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }

        //Se lee la respuesta
        int status = con.getResponseCode();
        
        //Verificamos si la respuesta del servidor fue exitosa
        if (status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_CREATED) {
            InputStream inputStream = con.getInputStream();//Si la respuesta es exitosa, obtenemos el InputStream de la conexión
            if (inputStream != null) {//Si el inputStrem no es null, se crea un BufferedReader para leer el flujo de entrada y convertirlo en texto
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));//InputStreamReader convierte los bytes del flujo de entrada en caracteres, usando UTF-8 como codificación
                StringBuilder response = new StringBuilder();//Creamos un StringBuilder para acumular las líneas leídas de la respuesta
                String responseLine;
                while ((responseLine = br.readLine()) != null) {//Leemos las líneas del BufferedReader una por una
                    response.append(responseLine.trim());//Se eliminan los espacios en blanco
                }

                //Se procesa la respuesta del JSON
                JSONObject jsonResponse = new JSONObject(response.toString());

                //Verifica si hay un access_token
                if (jsonResponse.has("access_token")) {
                    String accessToken = jsonResponse.getString("access_token");
                    String refreshToken = jsonResponse.getString("refresh_token");
                    mostrarMensaje("Login exitoso", "Bienvenido");
                    MenuController.initGui(); //Llama a initGui para abrir el menú
                } else {
                    mostrarMensaje("Error de autenticación", "Token no encontrado en la respuesta.");
                }
            } else {
                mostrarMensaje("Error de autenticación", "No se recibió una respuesta válida.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        mostrarMensaje("Error", "Hubo un problema con la conexión.");
      }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
