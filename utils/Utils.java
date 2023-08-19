package com.learntocodetogether.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.FieldPosition;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String doGet(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static int getAsInt(String intVal) {
        try {
            return Integer.parseInt(intVal);
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    public static String getResourceAsString(String path) {
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
            if (inputStream == null) {
                return "";
            }
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "";
        }
    }

    public static String get(String path) {
        try {
            Path p = Paths.get(path);
            try (BufferedReader reader = new BufferedReader(new FileReader(p.toString()))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    sb.append(line).append("\n");
                }
                return sb.toString();
            }
        } catch (Exception e) {
            return "";
        }
    }
}
