module com.untizio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.untizio to javafx.fxml;
    opens com.untizio.controller to javafx.fxml;
    exports com.untizio.controller to javafx.fxml;
    exports com.untizio;
}
