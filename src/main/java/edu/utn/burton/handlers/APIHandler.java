package edu.utn.burton.handlers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utn.burton.controller.Alerts;
import edu.utn.burton.controller.MenuController;
import edu.utn.burton.entities.Message;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javafx.scene.control.Alert;
import org.json.JSONObject;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class APIHandler {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public APIHandler() {
        //al leer datos desconocidos los ignora directamente brrr
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //metodo que devuelve una lista con los productos de los JSON
    public <T> List<T> get(Class<T> type, String consulta) throws Exception {
        // cree la conexio de la url 
        URL url = new URL(ConfigHandler.getInstance().getApi().url() + consulta);
        // se hace digamos la conexion
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // se obtiene normal 
        connection.setRequestMethod("GET");

        // esto lo que hace es que digamos si dura mas de un segundo el mae tira un error dispara excepcion 
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        //hagarra la respuesta de la api 
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        //acumula toda la vara en una sola linea 
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        //aqui lo que hace es convertir de jason a objetos en hava 
        //convierte tambien en una lista de productos  
        System.out.println("[GET] " + objectMapper.readValue(response.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, type)).toString());
        return objectMapper.readValue(response.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }

    public <T> List<T> post(Class<T> type, String query, JSONObject body) throws Exception {
        URL url = new URL(ConfigHandler.getInstance().getApi().url() + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }

        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_UNAUTHORIZED) {
            Alerts.show(new Message("Error de autenticación", "Correo o contraseña incorrectos."),Alert.AlertType.WARNING);
            return null;
        }

        if (status != HttpURLConnection.HTTP_OK && status != HttpURLConnection.HTTP_CREATED) {
            Alerts.show(new Message("Error", "Hubo un problema con la conexión o los datos proporcionados."),Alert.AlertType.WARNING);
            return null;
        }

        InputStream inputStream = connection.getInputStream();
        if (inputStream == null) {
            Alerts.show(new Message("Error de autenticación", "No se recibió una respuesta válida."),Alert.AlertType.WARNING);
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.has("access_token")) {
            String accessToken = jsonResponse.getString("access_token");
            String refreshToken = jsonResponse.getString("refresh_token");
            MenuController.initGui(); // Open the menu
        } else {
            Alerts.show(new Message("Error de autenticación", "Email o Contraseña incorrectos."),Alert.AlertType.WARNING);
        }

        return null;
    }
}
