package com.example.myjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHomePageApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Admin Home Page FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/myjavafx/AdminHomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Set up the primary stage
            primaryStage.setTitle("Admin Control Center");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
