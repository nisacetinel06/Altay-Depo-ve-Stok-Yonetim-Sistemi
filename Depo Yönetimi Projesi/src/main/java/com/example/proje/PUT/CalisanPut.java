package com.example.proje.PUT;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Calisan;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class CalisanPut implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("PUT".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStream inputStream = exchange.getRequestBody();
            String requestBody = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
            Calisan guncellenecekCalisan = new Calisan();

            if (jsonObject.has("calisan_id")) {
                guncellenecekCalisan.setCalisanId(jsonObject.get("calisan_id").getAsInt());
            }
            if (jsonObject.has("calisan_adi")) {
                guncellenecekCalisan.setCalisanAdi(jsonObject.get("calisan_adi").getAsString());
            }
            if (jsonObject.has("pozisyon")) {
                guncellenecekCalisan.setPozisyon(jsonObject.get("pozisyon").getAsString());
            }
            if (jsonObject.has("depo_id")) {
                guncellenecekCalisan.setDepoId(jsonObject.get("depo_id").getAsInt());
            }

            String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
            String username = "root";
            String password = "112606";
            DBconnection con = new DBconnection(url, username, password);
            Connection connection = null;
            String jsonResponse = "";

            try {
                connection = con.createConnection();
                String sql = "UPDATE calisan SET calisan_adi=?, pozisyon=?, depo_id=? WHERE calisan_id=?";
                PreparedStatement pstmt = connection.prepareStatement(sql);

                pstmt.setString(1, guncellenecekCalisan.getCalisanAdi());
                pstmt.setString(2, guncellenecekCalisan.getPozisyon());
                pstmt.setInt(3, guncellenecekCalisan.getDepoId());
                pstmt.setInt(4, guncellenecekCalisan.getCalisanId());

                int check = pstmt.executeUpdate();
                System.out.println("Update Result: " + check);

                if (check > 0) {
                    jsonResponse = "{\"status\": \"success\", \"message\": \"Çalışan başarıyla güncellendi!\"}";
                } else {
                    jsonResponse = "{\"status\": \"failure\", \"message\": \"Çalışan güncellenemedi!\"}";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonResponse = "{\"status\": \"error\", \"message\": \"Veritabanı hatası!\"}";
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                con.closeConnection();
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Yalnızca PUT yöntemine izin verildiğini belirten yanıt kodu
        }
    }
}
