package edu.utn.burton.controller;

import edu.utn.burton.Burton;
import edu.utn.burton.auth.GoogleAuthStrategy;
import edu.utn.burton.auth.PlatziAuthStrategy;
import edu.utn.burton.entities.Message;
import edu.utn.burton.entities.UserSession;
import edu.utn.burton.handlers.APIHandler;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

public class LoginController implements Initializable {

    @FXML
    private CustomTextField Correo;

    @FXML
    private CustomPasswordField Contraseña;

    @FXML
    private MFXButton Ingresar;
    
    @FXML
    private MFXButton IngresarInvitado;

    @FXML
    private MFXButton googleBtn;
    
    @FXML
    private MFXProgressSpinner loading;
    
    @FXML
    private MFXButton logout;

    APIHandler api = new APIHandler();

    //666
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Ingresar.setOnAction(ev -> {
            authenticateAsync(Correo.getText(), Contraseña.getText(), false);
        });

        googleBtn.setOnAction(ev -> {
            authenticateAsync(null, null, true);
        });
        
        IngresarInvitado.setOnAction(ev -> {
           Stage currentStage = (Stage) IngresarInvitado.getScene().getWindow();
           MenuController.initGui(currentStage);
           //currentStage.close();
        });
    }

    private void authenticateAsync(String email, String password, boolean isGoogleAuth) {
        Task<Boolean> authTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                if (isGoogleAuth) {
                    GoogleAuthStrategy googleAuth = new GoogleAuthStrategy();
                    return googleAuth.oAuth();
                } else {
                    PlatziAuthStrategy platziAuth = new PlatziAuthStrategy();
                    return platziAuth.auth(email, password);
                }
            }
        };

        authTask.setOnRunning(event -> {
            loading.setVisible(true);
            Ingresar.setDisable(true);
            googleBtn.setDisable(true);
        });

        // Procesar el resultado al finalizar el task
        authTask.setOnSucceeded(event -> {
            boolean isSuccess = authTask.getValue();
            if (isSuccess) {
                loadMenu();
            } else {
                Alerts.show(new Message(
                        "Error de autenticación",
                        isGoogleAuth ? "Ha ocurrido un error durante la autenticación con Google."
                                : "Email o Contraseña incorrectos."),
                        Alert.AlertType.WARNING
                );
            }
            Ingresar.setDisable(false);
            googleBtn.setDisable(false);
            loading.setVisible(false);
        });

        // Manejar errores durante la ejecución
        authTask.setOnFailed(event -> {
            Throwable exception = authTask.getException();
            exception.printStackTrace();
            Alerts.show(new Message("Error", "Ha ocurrido un error inesperado."), Alert.AlertType.ERROR);
            Ingresar.setDisable(false);
            googleBtn.setDisable(false);
            loading.setVisible(false);
        });

        // Ejecutar el task en un nuevo hilo
        Thread authThread = new Thread(authTask);
        authThread.setDaemon(true); // Permite que el hilo se detenga cuando la aplicación se cierra
        authThread.start();
        
    }

    private void loadMenu() {
        MenuController.initGui(new Stage());
        Stage stage = (Stage) Ingresar.getScene().getWindow();
        stage.close();
    }

    public static void logout(MFXButton button) {
    Stage currentStage = (Stage) button.getScene().getWindow();

    Alerts.showConfirmation(new Message("Cerrar Sesión", "¿Realmente quieres cerrar sesión?"), onResponse -> {
        if (onResponse == ButtonType.APPLY) {
            currentStage.close();
            UserSession.getInstance().logout();
        }
    });
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
