package com.georgiancollege.week05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DBCreds {
    public static String findUser() {
        try {
            Path filePath = Path.of("C:/OneDrive - Georgian College/Desktop/Java 03/Creds/user.txt");
            return Files.readString(filePath);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String findPass() {
        try {
            Path filePath = Path.of("C:/OneDrive - Georgian College/Desktop/Java 03/Creds/pass.txt");
            return Files.readString(filePath);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
