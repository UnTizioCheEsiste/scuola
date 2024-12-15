package com.untizio.controller;

import com.untizio.model.Teacher;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeacherEditDialogController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField materiaField;

    private Stage dialogStage;
    private Teacher teacher;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;

        nomeField.setText(teacher.getNome());
        cognomeField.setText(teacher.getCognome());
        materiaField.setText(teacher.getMateria());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            teacher.setNome(nomeField.getText());
            teacher.setCognome(cognomeField.getText());
            teacher.setMateria(materiaField.getText());

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

        if (nomeField.getText() == null || nomeField.getText().isEmpty() || !nomeField.getText().matches("[a-zA-Z]+")) {
            errorMessage += "Nome non valido! Deve contenere solo lettere.\n";
        }
        if (cognomeField.getText() == null || cognomeField.getText().isEmpty() || !cognomeField.getText().matches("[a-zA-Z]+")) {
            errorMessage += "Cognome non valido! Deve contenere solo lettere.\n";
        }
        if (materiaField.getText() == null || materiaField.getText().isEmpty()) {
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