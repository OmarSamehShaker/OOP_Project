package com.example.myjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the Enter Application screen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/myjavafx/EnterApplication.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Enter Application");
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
