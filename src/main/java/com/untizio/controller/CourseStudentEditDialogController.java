package com.untizio.controller;

import java.util.ArrayList;

import com.untizio.model.Course;
import com.untizio.model.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CourseStudentEditDialogController {

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> nomeColumn;
    @FXML
    private TableColumn<Student, String> cognomeColumn;
    @FXML
    private ButtonBar buttonBar;
    @FXML
    private Button handleAddStudent;
    @FXML
    private Button handleCancel;

    private Stage dialogStage;
    private Course course;
    private ObservableList<Student> allStudents = FXCollections.observableArrayList();
    private boolean okClicked = false;

    @FXML
    private void initialize() {
        // Initialize the student table with the columns.
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCourse(Course course) {
        this.course = course;
        studentTable.setItems(FXCollections.observableArrayList(course.getStudentiIscritti()));
    }

    public void setAllStudents(ObservableList<Student> students) {
        this.allStudents = students;
        studentTable.setItems(allStudents);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleAddStudent() {
        // Add a new student to the course
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            if (course.getStudentiIscritti() == null) {
                course.setStudentiIscritti(new ArrayList<>());
            }
            if (!course.getStudentiIscritti().contains(selectedStudent)) {
                course.aggiungiStudente(selectedStudent);
                studentTable.setItems(FXCollections.observableArrayList(course.getStudentiIscritti()));
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}