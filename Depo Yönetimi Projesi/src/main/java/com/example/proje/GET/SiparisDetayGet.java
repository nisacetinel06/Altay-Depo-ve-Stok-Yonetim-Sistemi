package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.SiparisDetay;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SiparisDetayGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("SiparisDetayGet handle çağrıldı");


        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
        String username = "root";
        String password = "112606";
        DBconnection con = new DBconnection(url, username, password);

        List<SiparisDetay> siparisListesi = new ArrayList<>();

        try (Connection connection = con.createConnection()) {
            // View'den verileri çekelim
            String query = "SELECT * FROM siparis_detay";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                SiparisDetay siparis = new SiparisDetay();
                siparis.setSiparis_id(rs.getInt("siparis_id"));
                siparis.setSiparis_tarihi(rs.getDate("siparis_tarihi"));
                siparis.setMusteri_adi(rs.getString("musteri_adi"));
                siparis.setMusteri_soyadi(rs.getString("musteri_soyadi"));

                siparisListesi.add(siparis);
            }

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }


        exchange.sendResponseHeaders(200, 0);
        os.write(gson.toJson(siparisListesi).getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
