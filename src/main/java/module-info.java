module com.doan.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    opens com.doan.client to javafx.fxml;
    exports com.doan.client;
    exports com.doan.client.Controller;
    opens com.doan.client.Controller to javafx.fxml;
}