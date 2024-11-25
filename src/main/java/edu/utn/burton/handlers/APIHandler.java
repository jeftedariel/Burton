package edu.utn.burton.handlers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utn.burton.controller.Alerts;
import edu.utn.burton.entities.Message;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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

    // List of Generic Objects (Like Barbie, could be what you want lol)
    public <T> List<T> getList(Class<T> type, String consulta, Map<String, String> headers) throws Exception {
        HttpURLConnection connection = setupConnection("GET", consulta, headers, null);
        String response = readResponse(connection); //Then Jackson`s ObjectMaper deserialze the json into the Object Class that we need
        return objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    }

    // Useful for GetUser via Token while using JWTAuth from api
    public <T> T getObject(Class<T> type, String consulta, Map<String, String> headers) throws Exception {
        HttpURLConnection connection = setupConnection("GET", consulta, headers, null);
        String response = readResponse(connection);
        return objectMapper.readValue(response, type);
    }
    
    public <T> JSONObject post(Class<T> type, String query, JSONObject body) throws Exception {
        HttpURLConnection connection = setupConnection("POST", query, null, body.toString());
        handleAuthentication(connection);
        //Then if everything is correct we`ll recieve the data from server
        String response = readResponse(connection);
        return (response != null) ? new JSONObject(response) : null;
    }

    private HttpURLConnection setupConnection(String method, String query, Map<String, String> headers, String body) throws Exception {
        // cree la conexio de la url 
        URL url = new URL(ConfigHandler.getInstance().getApi().url() + query);
        // se hace digamos la conexion
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Setea el metodo que se vaya a usar en la peticion (POST, GET)
        connection.setRequestMethod(method);
        // esto lo que hace es que digamos si dura mas de un segundo el mae tira un error dispara excepcion 
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        
        // Agrega headers a la consulta, en caso de q existan, esto pq se necesita para jalar 
        //La info del usuario con su Token
        if (headers != null) {
            headers.forEach(connection::setRequestProperty);
        }
        // Se coloca el body si la peticion es POST
        if (method.equals("POST")) {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            writeRequestBody(connection, body);
        }
        return connection;
    }

    private void writeRequestBody(HttpURLConnection connection, String body) throws Exception {
        if (body != null) { //For writing body into api call 666 Simple
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = body.getBytes();
                os.write(input, 0, input.length);
                os.flush();
            }
        }
    }

    private String readResponse(HttpURLConnection connection) throws Exception {
        //hagarra la respuesta de la api 
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            //acumula toda la vara en una sola linea
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
            return response.toString();
        }
    }

    private void handleAuthentication(HttpURLConnection connection) throws Exception {
        int status = connection.getResponseCode();

        if (status == HttpURLConnection.HTTP_UNAUTHORIZED) {
            Alerts.show(new Message("Error de autenticación", "Correo o contraseña incorrectos."), Alert.AlertType.WARNING);
        } else if (status != HttpURLConnection.HTTP_OK && status != HttpURLConnection.HTTP_CREATED) {
            Alerts.show(new Message("Error", "Hubo un problema con la conexión o los datos proporcionados."), Alert.AlertType.WARNING);
        }
    }
}
