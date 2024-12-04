package com.untizio.controller;

import com.untizio.App;
import com.untizio.model.Student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StudentOverviewController {

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

        // Add observable list data to the table
        studentTable.setItems(mainApp.getStudentData());
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
}