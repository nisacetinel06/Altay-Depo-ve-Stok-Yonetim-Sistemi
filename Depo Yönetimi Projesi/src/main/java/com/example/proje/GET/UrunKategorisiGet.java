package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.UrunKategorisi;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UrunKategorisiGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("UrunKategorisiGet handle çağrıldı");
        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        ResultSet rs = null;
        Statement statement = null;
        String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
        String password = "112606";
        String username = "root";
        DBconnection con = new DBconnection(url, username, password);

        List<UrunKategorisi> values = new ArrayList<>();
        try {

            statement = con.createConnection().createStatement();
            String sql = "SELECT * FROM urun_kategorisi";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                UrunKategorisi kategori = new UrunKategorisi();
                kategori.setKategori_id(rs.getInt("kategori_id"));
                kategori.setKategori_adi(rs.getString("kategori_adi"));
                values.add(kategori);


                System.out.println(kategori.getKategori_id());
                System.out.println(kategori.getKategori_adi());
            }

        } catch (SQLException e) {
            System.out.println("SQL Hatası: " + e.getMessage());
        } finally {
            con.closeConnection();
        }

        exchange.sendResponseHeaders(200, 0);
        os.write(gson.toJson(values).getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
