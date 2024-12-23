package com.example.myjavafx;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class EnterApplicationController {

    @FXML
    public void handleYes(ActionEvent event) throws Exception {
        // Load a new screen to choose Admin or Customer login/signup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myjavafx/Admin_Customer_Screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleNo(ActionEvent event) {
        // Exit the application
        System.exit(0);
    }
}
