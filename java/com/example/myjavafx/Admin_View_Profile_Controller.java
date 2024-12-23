package com.example.myjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
public class Admin_View_Profile_Controller {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label dobLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label workingHoursLabel;
    @FXML
    private Button returnButton;

    private Admin currentAdmin;
    private Database database;

    public Admin_View_Profile_Controller() {
        this.database = new Database(); 
    }

    public void initializeProfile(Admin admin) {
        this.currentAdmin = admin;

        if (currentAdmin != null) {
            usernameLabel.setText(currentAdmin.getUserName());
            dobLabel.setText(currentAdmin.getDateOfBirth());
            roleLabel.setText(currentAdmin.getRole());
            workingHoursLabel.setText(currentAdmin.getWorkingHours());
        } else {
            usernameLabel.setText("N/A");
            dobLabel.setText("N/A");
            roleLabel.setText("N/A");
            workingHoursLabel.setText("N/A");
        }
    }

    public void loadLoggedInAdminProfile(String username) {
        Admin admin = (Admin) database.getUserByUsername(username);

        if (admin != null) {
            initializeProfile(admin); 
        } else {
            System.out.println("Admin not found in the database.");
            initializeProfile(null); 
        }
    }

    @FXML
    void handleReturn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
