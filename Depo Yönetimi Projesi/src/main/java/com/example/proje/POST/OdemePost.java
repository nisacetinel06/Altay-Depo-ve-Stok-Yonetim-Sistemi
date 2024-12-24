package com.example.proje.POST;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Odeme;
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

public class OdemePost implements HttpHandler {

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
            Odeme yeniOdeme = new Odeme();

            if (jsonObject.has("odeme_id")) {
                yeniOdeme.setOdeme_id(jsonObject.get("odeme_id").getAsInt());
            }
            if (jsonObject.has("miktar")) {
                yeniOdeme.setMiktar(jsonObject.get("miktar").getAsDouble());
            }
            if (jsonObject.has("odeme_tarihi")) {
                yeniOdeme.setOdeme_tarihi(jsonObject.get("odeme_tarihi").getAsString());
            }
            if (jsonObject.has("odeme_tipi")) {
                yeniOdeme.setOdeme_tipi(jsonObject.get("odeme_tipi").getAsString());
            }
            if (jsonObject.has("siparis_id")) {
                yeniOdeme.setSiparis_id(jsonObject.get("siparis_id").getAsInt());
            }

            String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
            String username = "root";
            String password = "112606";
            DBconnection con = new DBconnection(url, username, password);
            Connection connection = null;

            String jsonResponse = "";
            try {
                connection = con.createConnection();
                String sql = "INSERT INTO odeme (odeme_id, miktar, odeme_tarihi, odeme_tipi, siparis_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, yeniOdeme.getOdeme_id());
                pstmt.setDouble(2, yeniOdeme.getMiktar());
                pstmt.setString(3, yeniOdeme.getOdeme_tarihi());
                pstmt.setString(4, yeniOdeme.getOdeme_tipi());
                pstmt.setInt(5, yeniOdeme.getSiparis_id());
                int check = pstmt.executeUpdate();
                System.out.println("Insert Result: " + check);

                if (check > 0) {
                    jsonResponse = "{\"status\": \"success\", \"message\": \"Ödeme başarıyla eklendi!\"}";
                } else {
                    jsonResponse = "{\"status\": \"failure\", \"message\": \"Ödeme eklenemedi!\"}";
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
