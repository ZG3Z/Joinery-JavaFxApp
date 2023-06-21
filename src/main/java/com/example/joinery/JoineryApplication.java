/**
 * @Author: Zuzanna Gez
 */
package com.example.joinery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class JoineryApplication extends Application {

    /**
     * Method to start the JavaFX application.
     *
     * @param stage The Stage object representing the main application window.
     * @throws IOException If there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JoineryApplication.class.getResource("scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 500);
        stage.setTitle("ENTER A NEW ORDER");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method of the program. Launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        launch();
    }
}