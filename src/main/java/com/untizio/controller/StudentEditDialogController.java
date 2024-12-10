package com.untizio.controller;

import com.untizio.model.Student;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentEditDialogController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField dataNascitaField;
    @FXML
    private TextField classeField;

    private Stage dialogStage;
    private Student student;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStudent(Student student) {
        this.student = student;

        nomeField.setText(student.getNome());
        cognomeField.setText(student.getCognome());
        dataNascitaField.setText(student.getDataNascita());
        classeField.setText(student.getClasse());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            student.setNome(nomeField.getText());
            student.setCognome(cognomeField.getText());
            student.setDataNascita(dataNascitaField.getText());
            student.setClasse(classeField.getText());

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

        if (nomeField.getText() == null || nomeField.getText().length() == 0) {
            errorMessage += "Nome non valido!\n"; 
        }
        if (cognomeField.getText() == null || cognomeField.getText().length() == 0) {
            errorMessage += "Cognome non valido!\n"; 
        }
        if (dataNascitaField.getText() == null || dataNascitaField.getText().length() == 0) {
            errorMessage += "Data di Nascita non valida!\n"; 
        }
        if (classeField.getText() == null || classeField.getText().length() == 0) {
            errorMessage += "Classe non valida!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campi non validi");
            alert.setHeaderText("Per favore correggi i campi non validi");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}