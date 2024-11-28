/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.utn.burton;

import edu.utn.burton.controller.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author jefte
 */
public class Burton extends Application {
    //666
    @Override
    public void start(Stage stage) throws Exception{
        //DumpData.dumpData();
        MenuController.initGui(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}