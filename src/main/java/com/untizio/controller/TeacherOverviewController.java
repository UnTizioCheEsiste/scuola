package com.untizio.controller;

import com.untizio.App;
import com.untizio.model.Course;
import com.untizio.model.Teacher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TeacherOverviewController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Teacher> teacherTable;
    @FXML
    private TableColumn<Teacher, Number> idColumn;
    @FXML
    private TableColumn<Teacher, String> nomeColumn;
    @FXML
    private TableColumn<Teacher, String> cognomeColumn;

    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> courseDescriptionColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label materiaLabel;

    // Reference to the main application
    private App app;

    private int lastTeacherId = 0;

    @FXML
    private void initialize() {
        // Initialize the teacher table with the columns.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        cognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());

        // Initialize the course table with the columns.
        courseNameColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        courseDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descrizioneProperty());

        // Clear teacher details.
        showTeacherDetails(null);

        // Listen for selection changes and show the teacher details when changed.
        teacherTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTeacherDetails(newValue));
    }

    public void setApp(App mainApp) {
        this.app = mainApp;

        // Initialize lastTeacherId with the highest existing teacher ID
        for (Teacher teacher : mainApp.getTeacherData()) {
            if (teacher.getId() > lastTeacherId) {
                lastTeacherId = teacher.getId();
            }
        }

        // Add observable list data to the table
        FilteredList<Teacher> filteredData = new FilteredList<>(mainApp.getTeacherData(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(teacher -> {
                // If filter text is empty, display all teachers.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare ID, name, and surname of every teacher with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(teacher.getId()).contains(lowerCaseFilter)) {
                    return true; // Filter matches ID.
                } else if (teacher.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (teacher.getCognome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Teacher> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(teacherTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        teacherTable.setItems(sortedData);
    }

    private void showTeacherDetails(Teacher teacher) {
        if (teacher != null) {
            idLabel.setText(Integer.toString(teacher.getId()));
            nomeLabel.setText(teacher.getNome());
            cognomeLabel.setText(teacher.getCognome());
            materiaLabel.setText(teacher.getMateria());

            // Set the course table items
            ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCorsiInsegnati());
            courseTable.setItems(courses);
        } else {
            idLabel.setText("");
            nomeLabel.setText("");
            cognomeLabel.setText("");
            materiaLabel.setText("");

            // Clear the course table
            courseTable.setItems(null);
        }
    }

    @FXML
    private void handleDeleteTeacher() {
        int selectedIndex = teacherTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ObservableList<Teacher> teacherData = app.getTeacherData();
            teacherData.remove(selectedIndex);
        } else {
            // Show a warning alert if no teacher is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Teacher Selected");
            alert.setContentText("Please select a teacher in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddTeacher() {
        lastTeacherId++; // Increment the lastTeacherId
        Teacher tempTeacher = new Teacher(lastTeacherId, "", "", "");
        boolean okClicked = app.showTeacherEditDialog(tempTeacher);
        if (okClicked) {
            if (isInputValid(tempTeacher)) {
                app.getTeacherData().add(tempTeacher);
            }
        }
    }

    @FXML
    private void handleEditTeacher() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            boolean okClicked = app.showTeacherEditDialog(selectedTeacher);
            if (okClicked) {
                if (isInputValid(selectedTeacher)) {
                    showTeacherDetails(selectedTeacher);
                }
            }
        } else {
            // Show a warning alert if no teacher is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Teacher Selected");
            alert.setContentText("Please select a teacher in the table.");
            alert.showAndWait();
        }
    }

    private boolean isInputValid(Teacher teacher) {
        String errorMessage = "";

        if (teacher.getNome() == null || teacher.getNome().isEmpty() || !teacher.getNome().matches("[a-zA-Z]+")) {
            errorMessage += "Nome non valido! Deve contenere solo lettere.\n";
        }
        if (teacher.getCognome() == null || teacher.getCognome().isEmpty() || !teacher.getCognome().matches("[a-zA-Z]+")) {
            errorMessage += "Cognome non valido! Deve contenere solo lettere.\n";
        }
        if (teacher.getMateria() == null || teacher.getMateria().isEmpty()) {
            errorMessage += "Materia non valida!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campi non validi");
            alert.setHeaderText("Per favore correggi i campi non validi");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}