package com.example.proje.POST;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Urun;
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

public class UrunPost implements HttpHandler {

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
            // Giriş verisini (JSON) okuyalım
            InputStream inputStream = exchange.getRequestBody();
            String requestBody = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
            Urun yeniUrun = new Urun();

            if (jsonObject.has("urun_id")) {
                yeniUrun.setUrun_id(jsonObject.get("urun_id").getAsInt());
            }
            if (jsonObject.has("urun_adi")) {
                yeniUrun.setUrun_adi(jsonObject.get("urun_adi").getAsString());
            }
            if (jsonObject.has("stok_miktari")) {
                yeniUrun.setStok_miktari(jsonObject.get("stok_miktari").getAsInt());
            }
            if (jsonObject.has("fiyat")) {
                yeniUrun.setFiyat(jsonObject.get("fiyat").getAsDouble());
            }
            if (jsonObject.has("kategori_id")) {
                yeniUrun.setKategori_id(jsonObject.get("kategori_id").getAsInt());
            }


            String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
            String username = "root";
            String password = "112606";
            DBconnection con = new DBconnection(url, username, password);
            Connection connection = null;

            String jsonResponse = "";
            try {
                connection = con.createConnection();
                String sql = "INSERT INTO urun (urun_id, urun_adi, stok_miktari, fiyat, kategori_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, yeniUrun.getUrun_id());
                pstmt.setString(2, yeniUrun.getUrun_adi());
                pstmt.setInt(3, yeniUrun.getStok_miktari());
                pstmt.setDouble(4, yeniUrun.getFiyat());
                pstmt.setInt(5, yeniUrun.getKategori_id());
                int check = pstmt.executeUpdate();
                System.out.println("Insert Result: " + check);

                if (check > 0) {
                    jsonResponse = "{\"status\": \"success\", \"message\": \"Ürün başarıyla eklendi!\"}";
                } else {
                    jsonResponse = "{\"status\": \"failure\", \"message\": \"Ürün eklenemedi!\"}";
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
