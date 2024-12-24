package com.example.proje.PUT;

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

public class UrunPut implements HttpHandler {
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
            Urun guncellenecekUrun = new Urun();

            if (jsonObject.has("urun_id")) {
                guncellenecekUrun.setUrun_id(jsonObject.get("urun_id").getAsInt());
            }
            if (jsonObject.has("urun_adi")) {
                guncellenecekUrun.setUrun_adi(jsonObject.get("urun_adi").getAsString());
            }
            if (jsonObject.has("stok_miktari")) {
                guncellenecekUrun.setStok_miktari(jsonObject.get("stok_miktari").getAsInt());
            }
            if (jsonObject.has("fiyat")) {
                guncellenecekUrun.setFiyat(jsonObject.get("fiyat").getAsDouble());
            }
            if (jsonObject.has("kategori_id")) {
                guncellenecekUrun.setKategori_id(jsonObject.get("kategori_id").getAsInt());
            }

            String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
            String username = "root";
            String password = "112606";
            DBconnection con = new DBconnection(url, username, password);
            Connection connection = null;
            String jsonResponse = "";

            try {
                connection = con.createConnection();
                String sql = "UPDATE urun SET urun_adi=?, stok_miktari=?, fiyat=?, kategori_id=? WHERE urun_id=?";
                PreparedStatement pstmt = connection.prepareStatement(sql);

                pstmt.setString(1, guncellenecekUrun.getUrun_adi());
                pstmt.setInt(2, guncellenecekUrun.getStok_miktari());
                pstmt.setDouble(3, guncellenecekUrun.getFiyat());
                pstmt.setInt(4, guncellenecekUrun.getKategori_id());
                pstmt.setInt(5, guncellenecekUrun.getUrun_id());

                int check = pstmt.executeUpdate();
                System.out.println("Update Result: " + check);

                if (check > 0) {
                    jsonResponse = "{\"status\": \"success\", \"message\": \"Ürün başarıyla güncellendi!\"}";
                } else {
                    jsonResponse = "{\"status\": \"failure\", \"message\": \"Ürün güncellenemedi!\"}";
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
