module com.untizio {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.untizio to javafx.fxml;
    exports com.untizio;
}
