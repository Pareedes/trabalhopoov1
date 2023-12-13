module JavaFXApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens poov.trabalhopoov to javafx.fxml, javafx.graphics;
    opens poov.trabalhopoov.models to javafx.base, javafx.graphics;
    exports poov.trabalhopoov;
}
