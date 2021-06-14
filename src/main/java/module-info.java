module gol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;
    exports gol;
    opens gol to javafx.fxml;
}
