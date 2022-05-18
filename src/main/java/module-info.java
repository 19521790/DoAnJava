module com.doan.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires gson;
    requires lombok;
    requires unirest.java;
    opens com.doan.client to javafx.fxml;
    exports com.doan.client;
    exports com.doan.client.Controller;
    opens com.doan.client.Controller to javafx.fxml;
    exports com.doan.client.Model;
    opens com.doan.client.Model to gson;
}