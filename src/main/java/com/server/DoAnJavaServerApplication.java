package com.server;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@SpringBootApplication
public class DoAnJavaServerApplication {

    public static void main(String[] args) {
        new java.io.File("dump").mkdirs();
        SpringApplication.run(DoAnJavaServerApplication.class, args);

        //delete dump folder when application stop
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                java.io.File file = new File("dump");
                String[]entries = file.list();
                for(String s: entries){
                    File currentFile = new File(file.getPath(),s);
                    currentFile.delete();
                }
            }
        });
    }
}
