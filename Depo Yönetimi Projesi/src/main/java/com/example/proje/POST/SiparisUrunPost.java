package com.example.proje.POST;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.SiparisUrun2;
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

public class SiparisUrunPost implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

            InputStream inputStream = exchange.getRequestBody();
            String requestBody = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
            SiparisUrun2 yeniSiparis = new SiparisUrun2();

            if (jsonObject.has("siparis_id")) {
                yeniSiparis.setSiparis_id(jsonObject.get("siparis_id").getAsInt());
            }
            if (jsonObject.has("urun_id")) {
                yeniSiparis.setUrun_id(jsonObject.get("urun_id").getAsInt());
            }
            if (jsonObject.has("miktar")) {
                yeniSiparis.setMiktar(jsonObject.get("miktar").getAsInt());
            }


            String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
            String username = "root";
            String password = "112606";
            DBconnection con = new DBconnection(url, username, password);
            Connection connection = null;

            String jsonResponse = "";
            try {
                connection = con.createConnection();
                String sql = "INSERT INTO siparis_urun (siparis_id, urun_id, miktar) VALUES (?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, yeniSiparis.getSiparis_id());
                pstmt.setInt(2, yeniSiparis.getUrun_id());
                pstmt.setInt(3, yeniSiparis.getMiktar());
                int check = pstmt.executeUpdate();
                System.out.println("Insert Result: " + check);

                if (check > 0) {

                    jsonResponse = "{\"status\": \"success\", \"message\": \"Sipariş başarıyla eklendi!\"}";
                } else {
                    jsonResponse = "{\"status\": \"failure\", \"message\": \"Sipariş eklenemedi!\"}";
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
            exchange.sendResponseHeaders(405, -1); // Yalnızca POST yöntemine izin verildiğini belirten yanıt kodu
        }
    }
}
