module com.example.domaci2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires com.google.gson;
    requires org.hildan.fxgson;

    opens com.example.rollingBall.arena to com.google.gson;
    exports com.example.rollingBall.arena;

    opens com.example.rollingBall.entities to javafx.fxml, javafx.graphics, com.google.gson;
    exports com.example.rollingBall.entities;

    opens com.example.rollingBall to javafx.fxml, javafx.graphics;
    exports com.example.rollingBall;
    exports com.example.rollingBall.menu;
    opens com.example.rollingBall.menu to javafx.fxml, javafx.graphics;
    exports com.example.rollingBall.prepare;
    opens com.example.rollingBall.prepare to com.google.gson, javafx.fxml, javafx.graphics;
}