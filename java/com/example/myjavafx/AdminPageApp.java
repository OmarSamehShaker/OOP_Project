package com.example.myjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminPageApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
        primaryStage.setScene(new Scene(loader.load(), 800, 600));
        primaryStage.setTitle("Admin Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
