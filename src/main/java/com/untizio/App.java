package com.untizio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.untizio.controller.CourseEditDialogController;
import com.untizio.controller.CourseOverviewController;
import com.untizio.controller.CourseStudentEditDialogController;
import com.untizio.controller.RootLayoutController;
import com.untizio.controller.StudentEditDialogController;
import com.untizio.controller.StudentOverviewController;
import com.untizio.controller.TeacherEditDialogController;
import com.untizio.controller.TeacherOverviewController;
import com.untizio.model.Course;
import com.untizio.model.Student;
import com.untizio.model.Teacher;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private ObservableList<Teacher> teacherData = FXCollections.observableArrayList();
    private ObservableList<Course> courseData = FXCollections.observableArrayList();

    public App() { }

    public ObservableList<Student> getStudentData() {
        return studentData;
    }

    public ObservableList<Teacher> getTeacherData() {
        return teacherData;
    }

    public ObservableList<Course> getCourseData() {
        return courseData;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gestionale Scuola By UnTizio");
        initRootLayout();
        if (rootLayout != null) {
            showStudentOverview();
            showTeacherOverview();
            showCourseOverview();
        }

        // Try to load last opened student file.
        File studentFile = getStudentFilePath();
        if (studentFile != null) {
            loadStudentDataFromFile(studentFile);
        }

        // Try to load last opened teacher file.
        File teacherFile = getTeacherFilePath();
        if (teacherFile != null) {
            loadTeacherDataFromFile(teacherFile);
        }

        // Try to load last opened course file.
        File courseFile = getCourseFilePath();
        if (courseFile != null) {
            loadCourseDataFromFile(courseFile);
        }

        this.primaryStage.setResizable(false);
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStudentOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/StudentOverview.fxml"));
            AnchorPane studentOverview = loader.load();

            TabPane tabPane = (TabPane) rootLayout.getCenter();
            Tab studentTab = tabPane.getTabs().get(0);
            AnchorPane studentContent = (AnchorPane) studentTab.getContent();

            studentContent.getChildren().clear();
            studentContent.getChildren().add(studentOverview);
            AnchorPane.setTopAnchor(studentOverview, 0.0);
            AnchorPane.setBottomAnchor(studentOverview, 0.0);
            AnchorPane.setLeftAnchor(studentOverview, 0.0);
            AnchorPane.setRightAnchor(studentOverview, 0.0);

            // Give the controller access to the main app.
            StudentOverviewController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTeacherOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/TeacherOverview.fxml"));
            AnchorPane teacherOverview = loader.load();

            TabPane tabPane = (TabPane) rootLayout.getCenter();
            Tab teacherTab = tabPane.getTabs().get(1);
            AnchorPane teacherContent = (AnchorPane) teacherTab.getContent();

            teacherContent.getChildren().clear();
            teacherContent.getChildren().add(teacherOverview);
            AnchorPane.setTopAnchor(teacherOverview, 0.0);
            AnchorPane.setBottomAnchor(teacherOverview, 0.0);
            AnchorPane.setLeftAnchor(teacherOverview, 0.0);
            AnchorPane.setRightAnchor(teacherOverview, 0.0);

            // Give the controller access to the main app.
            TeacherOverviewController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCourseOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/CourseOverview.fxml"));
            AnchorPane courseOverview = loader.load();

            TabPane tabPane = (TabPane) rootLayout.getCenter();
            Tab courseTab = tabPane.getTabs().get(2);
            AnchorPane courseContent = (AnchorPane) courseTab.getContent();

            courseContent.getChildren().clear();
            courseContent.getChildren().add(courseOverview);
            AnchorPane.setTopAnchor(courseOverview, 0.0);
            AnchorPane.setBottomAnchor(courseOverview, 0.0);
            AnchorPane.setLeftAnchor(courseOverview, 0.0);
            AnchorPane.setRightAnchor(courseOverview, 0.0);

            // Give the controller access to the main app.
            CourseOverviewController controller = loader.getController();
            controller.setApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showStudentEditDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/StudentEditDialog.fxml"));
            DialogPane page = loader.load();
    
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

    public boolean showTeacherEditDialog(Teacher teacher) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/TeacherEditDialog.fxml"));
            DialogPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Insegnante");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TeacherEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTeacher(teacher);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCourseEditDialog(Course course) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/CourseEditDialog.fxml"));
            DialogPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Corso");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CourseEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCourse(course);
            controller.setApp(this); // Pass the instance of App to the controller

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCourseStudentEditDialog(Course course, ObservableList<Student> allStudents) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/CourseStudentEditDialog.fxml"));
            DialogPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Studenti Corso");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CourseStudentEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCourse(course);
            controller.setAllStudents(allStudents);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getStudentFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setStudentFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("Gestionale Scuola By UnTizio - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("Gestionale Scuola By UnTizio");
        }
    }

    public void loadStudentDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Student> studentList = mapper.readValue(file, new TypeReference<List<Student>>() {});
            studentData.clear();
            studentData.addAll(studentList);

            setStudentFilePath(file);
        } catch (IOException e) {
            System.out.println("Student Loading Error: " + e);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile caricare i dati");
            alert.setContentText("Non è stato possibile caricare i dati dal file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveStudentDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, studentData);

            setStudentFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile salvare i dati");
            alert.setContentText("Non è stato possibile salvare i dati nel file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public File getTeacherFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        String filePath = prefs.get("teacherFilePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setTeacherFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        if (file != null) {
            prefs.put("teacherFilePath", file.getPath());
            primaryStage.setTitle("Gestionale Scuola By UnTizio - " + file.getName());
        } else {
            prefs.remove("teacherFilePath");
            primaryStage.setTitle("Gestionale Scuola By UnTizio");
        }
    }

    public void loadTeacherDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Teacher> teacherList = mapper.readValue(file, new TypeReference<List<Teacher>>() {});
            teacherData.setAll(teacherList);
    
            setTeacherFilePath(file);
        } catch (IOException e) {
            System.out.println("Teacher Loading Error: " + e);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile caricare i dati");
            alert.setContentText("Non è stato possibile caricare i dati dal file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public void saveTeacherDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
    
            // Convert ObservableList to List before serialization
            List<Teacher> teachers = new ArrayList<>(teacherData);
            mapper.writeValue(file, teachers);
    
            setTeacherFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile salvare i dati");
            alert.setContentText("Non è stato possibile salvare i dati nel file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public File getCourseFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        String filePath = prefs.get("courseFilePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setCourseFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        if (file != null) {
            prefs.put("courseFilePath", file.getPath());
            primaryStage.setTitle("Gestionale Scuola By UnTizio - " + file.getName());
        } else {
            prefs.remove("courseFilePath");
            primaryStage.setTitle("Gestionale Scuola By UnTizio");
        }
    }

    public void loadCourseDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Course> courseList = mapper.readValue(file, new TypeReference<List<Course>>() {});
            courseData.setAll(courseList);
    
            setCourseFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile caricare i dati");
            alert.setContentText("Non è stato possibile caricare i dati dal file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public void saveCourseDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
    
            // Convert ObservableList to List before serialization
            List<Course> courses = new ArrayList<>(courseData);
            mapper.writeValue(file, courses);
    
            setCourseFilePath(file);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile salvare i dati");
            alert.setContentText("Non è stato possibile salvare i dati nel file:\n" + file.getPath());
    
            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}