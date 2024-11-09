package edu.utn.burton.handlers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utn.burton.entities.Category;
import edu.utn.burton.entities.Product;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */
public class APIHandler<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> type;

    public APIHandler(Class<T> type) {
        this.type = type;
        //al leer datos desconocidos los ignora directamente brrr
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

//metodo que devuelve una lista con los productos de los JSON
    public List<T> obtenerProductos(String consulta) throws Exception {
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
        System.out.println(objectMapper.readValue(response.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class)).toString());
        return objectMapper.readValue(response.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

    }

    public List<T> getCategories(String consulta) throws Exception {
        URL url = new URL(ConfigHandler.getInstance().getApi().url() + consulta);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        System.out.println(objectMapper.readValue(response.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Category.class)).toString());
        return objectMapper.readValue(response.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, Category.class));

    }
}
