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
     * Creates an empty teacher list.
     */
    @FXML
    private void handleNewTeacher() {
        app.getTeacherData().clear();
        app.setTeacherFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select a teacher list to load.
     */
    @FXML
    private void handleOpenTeacher() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File teacherFile = app.getTeacherFilePath();
        if (teacherFile != null) {
            fileChooser.initialFileNameProperty().set(teacherFile.getName());
        }

        // Show open file dialog
        File file = fileChooser.showOpenDialog(app.getPrimaryStage());

        if (file != null) {
            app.loadTeacherDataFromFile(file);
        }
    }

    /**
     * Saves the file to the teacher file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSaveTeacher() {
        File teacherFile = app.getTeacherFilePath();
        if (teacherFile != null) {
            app.saveTeacherDataToFile(teacherFile);
        } else {
            handleSaveAsTeacher();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAsTeacher() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(app.getPrimaryStage());

        if (file != null) {
            app.saveTeacherDataToFile(file);
        }
    }

    /**
     * Handles the action of creating a new course.
     * This method clears the current course data and resets the course file path to null.
     * It is typically called when the user wants to start a new course.
     */
    @FXML
    private void handleNewCourse() {
        app.getCourseData().clear();
        app.setCourseFilePath(null);
    }

    /**
     * Opens a file chooser dialog to select a JSON file and loads course data from the selected file.
     * 
     * This method is triggered by an FXML event. It creates a FileChooser instance, sets a filter to 
     * only show JSON files, and opens the file chooser dialog. If a file is selected, it calls the 
     * application's method to load course data from the selected file.
     */
    @FXML
    private void handleOpenCourse() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(app.getPrimaryStage());

        if (file != null) {
            app.loadCourseDataFromFile(file);
        }
    }

    /**
     * Handles the action of saving the current course data.
     * If a course file path is already set, it saves the course data to that file.
     * Otherwise, it prompts the user to specify a file path to save the course data.
     */
    @FXML
    private void handleSaveCourse() {
        File courseFile = app.getCourseFilePath();
        if (courseFile != null) {
            app.saveCourseDataToFile(courseFile);
        } else {
            handleSaveCourseAs();
        }
    }

    /**
     * Opens a FileChooser dialog to save the course data as a JSON file.
     * The method sets a filter to only allow saving files with a .json extension.
     * If the user selects a file without the .json extension, it appends the extension to the file name.
     * Finally, it calls the application's method to save the course data to the selected file.
     */
    @FXML
    private void handleSaveCourseAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(app.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            app.saveCourseDataToFile(file);
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