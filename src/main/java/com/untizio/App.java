package com.untizio;

import java.io.IOException;

import com.untizio.controller.StudentEditDialogController;
import com.untizio.controller.StudentOverviewController;
import com.untizio.model.Student;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    public App() 
    {
        studentData.add(new Student(0, "Paolino", "Merlino", "20020110", "2A"));
    }

    public ObservableList<Student> getStudentData() 
    {
        return studentData;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gestionale Scuola By UnTizio");
        initRootLayout();
        showPersonOverview();
    }

    /**
     * Initializes the root layout by loading it from the FXML file and setting it as the scene of the primary stage.
     * This method attempts to load the "RootLayout.fxml" file using the FXMLLoader.
     * If successful, it sets the loaded BorderPane as the root layout and displays it in the primary stage.
     * If an IOException occurs during loading, the stack trace is printed.
     */
    public void initRootLayout()
    {
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Loads and displays the StudentOverview.fxml file in the center of the root layout.
     * This method uses an FXMLLoader to load the FXML file and sets the loaded AnchorPane
     * as the center of the root layout. If an IOException occurs during loading, the stack
     * trace is printed.
     */
    public void showPersonOverview() {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("view/StudentOverview.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        rootLayout.setCenter(personOverview);

        // Give the controller access to the main app.
        StudentOverviewController controller = loader.getController();
        controller.setApp(this);
    } catch (IOException e) { e.printStackTrace(); }
    }

    public Stage getPrimaryStage() 
    {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens a dialog to edit details for the specified student. 
     * If the user clicks Conferma, the changes are saved into the provided student object.
     *
     * @param student the student object to be edited
     * @return true if the user clicked OK, false otherwise
     */
    public boolean showStudentEditDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/StudentEditDialog.fxml"));
            DialogPane page = (DialogPane) loader.load();
    
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Student");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
    
            StudentEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStudent(student);
    
            dialogStage.showAndWait();
    
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}