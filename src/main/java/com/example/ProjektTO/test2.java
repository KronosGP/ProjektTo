package com.example.ProjektTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class test2 {
    public static void main(String[] args) {
        try {
            // Tworzenie URL docelowego
            URL url = new URL("http://localhost:8080/db/connect"); // Ustaw adres URL serwera

            // Otwieranie połączenia HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Dane do przesłania w formie JSON (możesz użyć innych formatów)
            String jsonData = "{\"ip\":\"127.0.0.1\",\"port\":\"3306\",\"username\":\"root\",\"password\":\"qwertyasd\",\"name\":\"test\"}";

            // Ustawienie nagłówka Content-Type
            connection.setRequestProperty("Content-Type", "application/json");

            // Przesłanie danych do serwera
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            // Odczytanie odpowiedzi z serwera
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Wyświetlenie odpowiedzi
            System.out.println("Response from server:");
            System.out.println(response.toString());
            url = new URL("http://localhost:8080/db/disconnect"); // Ustaw adres URL serwera

            // Otwieranie połączenia HTTP
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            // Zamknięcie połączenia
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
