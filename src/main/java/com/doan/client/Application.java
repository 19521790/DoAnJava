/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.doan.client;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Admin
 */
public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws IOException, UnirestException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/MainScreen.fxml"));
//        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/com/doan/client/View/AdminScreen/AdminScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/doan/client/application.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResource("/com/doan/client/Image/icon.jpg").toExternalForm()));
        primaryStage.setTitle("Zyan");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
