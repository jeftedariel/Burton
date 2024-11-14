/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.auth;

import edu.utn.burton.controller.Alerts;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.User;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.handlers.APIHandler;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import org.json.JSONObject;

/**
 *
 * @author jefte
 */
public class PlatziAuthStrategy implements LoginStrategy{

    @Override
    public boolean auth(String email, String password) {
        APIHandler api = new APIHandler();
        JSONObject credenciales = new JSONObject();
        credenciales.put("email", email);
        credenciales.put("password", password);

        try {
            JSONObject user = api.post(User.class, "auth/login", credenciales);
            if (user.has("access_token")) {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+user.getString("access_token"));
                
                User usr = api.getObject(User.class, "auth/profile",headers );
                
                UserSession.getInstance().login(usr.id(), usr.name(), usr.email(), usr.avatar(), usr.role());
                System.out.println(UserSession.getInstance().toString());
                return true;
            } else {
                Alerts.show(new Message("Error de autenticación", "Email o Contraseña incorrectos."), Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
        return false;
    }

    @Override
    public boolean oAuth() {
        throw new UnsupportedOperationException("Esta autenticación no soporta el uso de Token. Utilice auth(email, password)");
    }
    
}
