module org.example.ejerciciopersona {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ejerciciopersona to javafx.fxml;
    exports org.example.ejerciciopersona;
}