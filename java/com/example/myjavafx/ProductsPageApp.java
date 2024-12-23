package com.example.myjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductsPageApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Products Page FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductsPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            // Set up the primary stage
            primaryStage.setTitle("Products Page");
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
