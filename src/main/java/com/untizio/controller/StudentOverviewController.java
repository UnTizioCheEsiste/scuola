package com.untizio.controller;

import com.untizio.App;
import com.untizio.model.Student;

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

public class StudentOverviewController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Number> idColumn;
    @FXML
    private TableColumn<Student, String> nomeColumn;
    @FXML
    private TableColumn<Student, String> cognomeColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label dataNascitaLabel;
    @FXML
    private Label classeLabel;

    // Reference to the main application
    private App app;

    private int lastStudentId = 0;

    public StudentOverviewController() {
    }

    @FXML
    private void initialize() {
        // Initialize the student table with the columns.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        cognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());

        // Clear student details.
        showStudentDetails(null);

        // Listen for selection changes and show the student details when changed.
        studentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showStudentDetails(newValue));
    }

    public void setApp(App mainApp) {
        this.app = mainApp;

        // Initialize lastStudentId with the highest existing student ID
        for (Student student : mainApp.getStudentData()) {
            if (student.getId() > lastStudentId) {
                lastStudentId = student.getId();
            }
        }

        // Add observable list data to the table
        FilteredList<Student> filteredData = new FilteredList<>(mainApp.getStudentData(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If filter text is empty, display all students.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare ID, name, and surname of every student with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(student.getId()).contains(lowerCaseFilter)) {
                    return true; // Filter matches ID.
                } else if (student.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (student.getCognome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Student> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(studentTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        studentTable.setItems(sortedData);
    }

    private void showStudentDetails(Student student) {
        if (student != null) {
            idLabel.setText(Integer.toString(student.getId()));
            nomeLabel.setText(student.getNome());
            cognomeLabel.setText(student.getCognome());
            dataNascitaLabel.setText(student.getDataNascita());
            classeLabel.setText(student.getClasse());
        } else {
            idLabel.setText("");
            nomeLabel.setText("");
            cognomeLabel.setText("");
            dataNascitaLabel.setText("");
            classeLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteStudent() {
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ObservableList<Student> studentData = app.getStudentData();
            studentData.remove(selectedIndex);
        } else {
            // Show a warning alert if no student is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddStudent() {
        lastStudentId++; // Increment the lastStudentId
        Student tempStudent = new Student(lastStudentId, "", "", "", "");
        boolean okClicked = app.showStudentEditDialog(tempStudent);
        if (okClicked) {
            app.getStudentData().add(tempStudent);
        }
    }

    @FXML
    private void handleEditStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean okClicked = app.showStudentEditDialog(selectedStudent);
            if (okClicked) {
                showStudentDetails(selectedStudent);
            }
        } else {
            // Show a warning alert if no student is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student in the table.");
            alert.showAndWait();
        }
    }
}