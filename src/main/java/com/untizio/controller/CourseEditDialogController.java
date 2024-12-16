package com.untizio.controller;

import com.untizio.App;
import com.untizio.model.Course;
import com.untizio.model.Teacher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CourseEditDialogController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField descrizioneField;
    @FXML
    private ComboBox<Teacher> insegnanteComboBox;

    private Stage dialogStage;
    private Course course;
    private boolean okClicked = false;
    private ObservableList<Teacher> teacherData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set the StringConverter for the ComboBox to display only the name and surname of the teacher
        insegnanteComboBox.setConverter(new StringConverter<Teacher>() {
            @Override
            public String toString(Teacher teacher) {
                if (teacher == null) {
                    return null;
                }
                return teacher.getNome() + " " + teacher.getCognome();
            }

            @Override
            public Teacher fromString(String string) {
                return null; // No need to convert from String to Teacher
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCourse(Course course) {
        this.course = course;

        nomeField.setText(course.getNome());
        descrizioneField.setText(course.getDescrizione());
        insegnanteComboBox.setItems(teacherData);
        insegnanteComboBox.setValue(course.getInsegnante());
    }

    public void setApp(App app) {
        this.teacherData = app.getTeacherData();
        insegnanteComboBox.setItems(teacherData);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            course.setNome(nomeField.getText());
            course.setDescrizione(descrizioneField.getText());
            course.setInsegnante(insegnanteComboBox.getValue());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomeField.getText() == null || nomeField.getText().isEmpty()) {
            errorMessage += "Nome non valido!\n";
        }
        if (descrizioneField.getText() == null || descrizioneField.getText().isEmpty()) {
            errorMessage += "Descrizione non valida!\n";
        }
        if (insegnanteComboBox.getValue() == null) {
            errorMessage += "Insegnante non valido!\n";
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