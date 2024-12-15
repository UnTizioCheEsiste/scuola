package com.untizio.controller;

import java.io.File;

import com.untizio.App;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootLayoutController {

    // Reference to the main application
    private App app;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param app
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * Creates an empty student list.
     */
    @FXML
    private void handleNewStudent() {
        app.getStudentData().clear();
        app.setStudentFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select a student list to load.
     */
    @FXML
    private void handleOpenStudent() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File studentFile = app.getStudentFilePath();
        if (studentFile != null) {
            fileChooser.initialFileNameProperty().set(studentFile.getName());
        }

        // Show open file dialog
        File file = fileChooser.showOpenDialog(app.getPrimaryStage());

        if (file != null) {
            app.loadStudentDataFromFile(file);
        }
    }

    /**
     * Saves the file to the student file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveStudent() {
        File studentFile = app.getStudentFilePath();
        if (studentFile != null) {
            app.saveStudentDataToFile(studentFile);
        } else {
            handleSaveAsStudent();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsStudent() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File studentFile = app.getStudentFilePath();
        if (studentFile != null) {
            fileChooser.initialFileNameProperty().set(studentFile.getName());
        }

        // Show save file dialog
        File file = fileChooser.showSaveDialog(app.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            app.saveStudentDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Student Management");
        alert.setHeaderText("About");
        alert.setContentText("Author: Nicolò Ciancaglia\nGitHub: github.com/UnTizioCheEsiste");
        alert.showAndWait();
    }
}