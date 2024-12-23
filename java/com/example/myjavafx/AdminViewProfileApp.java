package com.example.myjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminViewProfileApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML for Admin View Profile page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin_View_Profile.fxml"));
        VBox root = loader.load(); // Changed from AnchorPane to VBox

        // Set up the scene and the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin View Profile");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}