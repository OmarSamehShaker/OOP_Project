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
        // Initialize the database
        this.database = new Database(); // Ensure Database is loaded correctly
    }

    /**
     * Populates the labels using the Admin's profile.
     *
     * @param admin The current admin whose profile is to be displayed.
     */
    public void initializeProfile(Admin admin) {
        this.currentAdmin = admin;

        if (currentAdmin != null) {
            // Populate the labels from the Admin fields
            usernameLabel.setText(currentAdmin.getUserName());
            dobLabel.setText(currentAdmin.getDateOfBirth());
            roleLabel.setText(currentAdmin.getRole());
            workingHoursLabel.setText(currentAdmin.getWorkingHours());
        } else {
            // Handle the case where the Admin object is null
            usernameLabel.setText("N/A");
            dobLabel.setText("N/A");
            roleLabel.setText("N/A");
            workingHoursLabel.setText("N/A");
        }
    }

    /**
     * Loads the logged-in Admin's profile from the database and initializes the view.
     */
    public void loadLoggedInAdminProfile(String username) {
        // Get the admin by username from the database
        Admin admin = (Admin) database.getUserByUsername(username);

        if (admin != null) {
            initializeProfile(admin); // If admin found, display the profile
        } else {
            // Handle the case where the admin is not found
            System.out.println("Admin not found in the database.");
            initializeProfile(null); // Display N/A if not found
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
