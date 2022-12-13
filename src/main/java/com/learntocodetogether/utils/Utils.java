package com.learntocodetogether.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils {
    public static String doGet(String uri) {
        try(CloseableHttpClient client = HttpClients.createDefault()
        ) {
            HttpGet httpGet = new HttpGet(uri) ;
            CloseableHttpResponse execute = client.execute(httpGet);
            return execute.toString();
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
}
