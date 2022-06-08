module com.doan.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires gson;
    requires lombok;
    requires unirest.java;
    requires java.mail;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires  de.jensd.fx.glyphs.octicons;
    requires imageio.jxl;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.materialicons;
    requires com.jfoenix;
    requires  javafx.media;


    opens com.doan.client to javafx.fxml;
    exports com.doan.client;
    exports com.doan.client.Controller;
    opens com.doan.client.Controller to javafx.fxml;
    exports com.doan.client.Model;
    opens com.doan.client.Model to gson;
    exports com.doan.client.Controller.UserScreen;
    opens com.doan.client.Controller.UserScreen to javafx.fxml;
    exports com.doan.client.Controller.Component;
    opens com.doan.client.Controller.Component to javafx.fxml;
    exports com.doan.client.Controller.AdminScreen;
    opens com.doan.client.Controller.AdminScreen to javafx.fxml;
    exports com.doan.client.Model.Object;
    opens com.doan.client.Model.Object to javafx.fxml;


}