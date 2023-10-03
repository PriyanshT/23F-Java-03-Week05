module com.georgiancollege.week05 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.georgiancollege.week05 to javafx.fxml;
    exports com.georgiancollege.week05;
}