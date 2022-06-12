package com.server.service.data;

import com.server.exception.FileFormatException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.NotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
public class DataService {

    private static String RESOURCES_PATH = "src/main/resources/";
    private static String DATA_PATH = "src/main/resources/data/";
    private static String IMAGE_PATH = "src/main/resources/image/";

    private static String LOCAL_PATH = "http://localhost:8080/";

    public String storeData(MultipartFile multipartFile, String mimeType) throws IOException, FileFormatException {
        String fileName = UUID.randomUUID().toString();

        if (mimeType == ".m4a") {
            java.io.File convertFile = new java.io.File(DATA_PATH + fileName + mimeType);
            convertFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convertFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            return LOCAL_PATH + "data/" + fileName + mimeType;
        } else if (mimeType == ".jpg") {
            java.io.File convertFile = new java.io.File(IMAGE_PATH + fileName + mimeType);
            convertFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convertFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            return LOCAL_PATH + "image/" + fileName + mimeType;
        } else {
            throw new FileFormatException(FileFormatException.NotFoundException(mimeType));
        }
    }

    public void deleteData(String dataPath) throws FileFormatException {
        java.io.File fileToDelete = new java.io.File(RESOURCES_PATH + dataPath.substring(LOCAL_PATH.length()));
        System.out.println(DATA_PATH + dataPath.substring(LOCAL_PATH.length()));
        if (fileToDelete.delete()) {
            System.out.println("successfully delete file " + dataPath);
        } else {
            System.out.println("Cant delete file " + dataPath);
        }
    }
}
