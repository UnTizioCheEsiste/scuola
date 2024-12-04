module com.untizio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.untizio to javafx.fxml;
    opens com.untizio.controller to javafx.fxml;
    exports com.untizio.controller to javafx.fxml;
    exports com.untizio;
}
