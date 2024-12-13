module com.untizio {
        requires javafx.controls;
        requires javafx.fxml;
        requires com.fasterxml.jackson.databind;
        requires com.fasterxml.jackson.core;
        requires com.fasterxml.jackson.datatype.jsr310;
        requires java.prefs;
    
        opens com.untizio to javafx.fxml;
        opens com.untizio.controller to javafx.fxml;
        opens com.untizio.model to com.fasterxml.jackson.databind;
    
        exports com.untizio;
        exports com.untizio.controller;
        exports com.untizio.model;
}
