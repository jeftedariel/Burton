/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.burton.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import edu.utn.burton.entities.UserSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author jefte
 */
public class GoogleAuthStrategy implements LoginStrategy{
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    @Override
    public boolean auth(String email, String password) {
        throw new UnsupportedOperationException("Esta autenticación no soporta el ingreso con correo/contraseña. Utilice authToken(token).");
    }

    @Override
    public boolean oAuth() {
        try {
            Userinfoplus userInfo = authenticateUser();
            if (userInfo != null) {
                
                UserSession.getInstance().login(Objects.hash(userInfo.getId()), userInfo.getName(), userInfo.getEmail(), userInfo.getPicture(), "customer");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return false;
    }
    
    private Userinfoplus authenticateUser() throws GeneralSecurityException, IOException {
        // Cargar el archivo credentials.json 
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("googleAuth.json");

        if (inputStream == null) {
            throw new IOException("No se pudo encontrar el archivo credentials.json");
        }
        InputStreamReader reader = new InputStreamReader(inputStream);
        //Carga los token para acceder al api de google
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, reader);
        //Se instancia un "flow" q es basicamente el flujo q debera seguir la app para validar q la persona existe y sus datos 
        //Son correctos
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets, 
                Arrays.asList("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email"))
                .setAccessType("offline")
                .build();
        
        //A grandes razgos aqui es donde se nos ejecuta la magia para abrir el navegador :)
        // pq crea un new LocalServerReceiver() que es la pagina en localhost q se nos abre
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        // Aqui ya se usa el Credential q nos devolvio recien, ya con este accedemos a la api de google para pedir
        // la data del mae (ahorita solo saco correo nombre y foto pero se puede obtener una q otra cosa más)
        Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .build();

        return oauth2.userinfo().get().execute();
    }
    
}
