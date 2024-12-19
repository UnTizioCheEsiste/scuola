package com.untizio.controller;

import java.util.ArrayList;
import java.util.List;

import com.untizio.App;
import com.untizio.model.Course;
import com.untizio.model.Student;
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

public class CourseOverviewController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, Number> idColumn;
    @FXML
    private TableColumn<Course, String> nomeColumn;
    @FXML
    private TableColumn<Course, String> descrizioneColumn;

    @FXML
    private Label idLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label descrizioneLabel;
    @FXML
    private Label insegnanteLabel;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentNomeColumn;
    @FXML
    private TableColumn<Student, String> studentCognomeColumn;

    private App app;
    private ObservableList<Course> courseData = FXCollections.observableArrayList();
    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    private int lastCourseId = 0;

    @FXML
    private void initialize() {
        // Initialize the course table with the columns.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        descrizioneColumn.setCellValueFactory(cellData -> cellData.getValue().descrizioneProperty());

        // Initialize the student table with the columns.
        studentNomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        studentCognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());

        // Clear course details.
        showCourseDetails(null);

        // Listen for selection changes and show the course details when changed.
        courseTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCourseDetails(newValue));
    }

    public void setApp(App app) {
        this.app = app;

        // Initialize lastCourseId with the highest existing course ID
        for (Course course : app.getCourseData()) {
            if (course.getId() > lastCourseId) {
                lastCourseId = course.getId();
            }
        }

        // Add observable list data to the table
        FilteredList<Course> filteredData = new FilteredList<>(app.getCourseData(), p -> true);

        // Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(course -> {
                // If filter text is empty, display all courses.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare ID, name, and description of every course with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(course.getId()).contains(lowerCaseFilter)) {
                    return true; // Filter matches ID.
                } else if (course.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                } else if (course.getDescrizione().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches description.
                }
                return false; // Does not match.
            });
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Course> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(courseTable.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        courseTable.setItems(sortedData);
    }

    private void showCourseDetails(Course course) {
        if (course != null) {
            idLabel.setText(Integer.toString(course.getId()));
            nomeLabel.setText(course.getNome());
            descrizioneLabel.setText(course.getDescrizione());
            if (course.getInsegnante() != null) {
                insegnanteLabel.setText(course.getInsegnante().getNome() + " " + course.getInsegnante().getCognome());
            } else {
                insegnanteLabel.setText("");
            }

            // Set the student table items
            ObservableList<Student> students = (ObservableList<Student>) course.getStudentiIscritti();
            if (students != null && students.isEmpty()) {
                Student placeholderStudent = new Student(0, "nessun", "iscritto", null, null);
                students.add(placeholderStudent);
            }
            studentTable.setItems(students);
        } else {
            idLabel.setText("");
            nomeLabel.setText("");
            descrizioneLabel.setText("");
            insegnanteLabel.setText("");

            // Clear the student table
            studentTable.setItems(null);
        }
    }

    @FXML
    private void handleAddCourse() {
        lastCourseId++; // Increment the lastCourseId
        Course tempCourse = new Course(lastCourseId, "", "");
        boolean okClicked = app.showCourseEditDialog(tempCourse);
        if (okClicked) {
            app.getCourseData().add(tempCourse);
        }
    }

    @FXML
    private void handleEditCourse() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean okClicked = app.showCourseEditDialog(selectedCourse);
            if (okClicked) {
                showCourseDetails(selectedCourse);
            }
        } else {
            // Show a warning alert if no course is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Course Selected");
            alert.setContentText("Please select a course in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteCourse() {
        int selectedIndex = courseTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Course selectedCourse = courseTable.getItems().get(selectedIndex);

            // Remove the course from the teacher's list of courses
            Teacher teacher = selectedCourse.getInsegnante();
            if (teacher != null) {
                List<Course> mutableCourses = new ArrayList<>(teacher.getCorsiInsegnati());
                mutableCourses.remove(selectedCourse);
                teacher.setCorsi(mutableCourses);
            }

            // Remove the course from each student's list of courses
            for (Student student : selectedCourse.getStudentiIscritti()) {
                student.getCorsi().remove(selectedCourse);
            }

            // Remove the course from the main course list
            app.getCourseData().remove(selectedCourse);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun Corso Selezionato");
            alert.setContentText("Per favore, seleziona un corso nella tabella.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddStudent() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean okClicked = app.showCourseStudentEditDialog(selectedCourse, app.getStudentData());
            if (okClicked) {
                showCourseDetails(selectedCourse);
            }
        } else {
            // Show a warning alert if no course is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Course Selected");
            alert.setContentText("Please select a course in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteStudent() {
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Student selectedStudent = studentTable.getItems().get(selectedIndex);
            Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
            if (selectedCourse != null) {
                selectedCourse.rimuoviStudente(selectedStudent);
                studentTable.getItems().remove(selectedIndex);
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