package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.SiparisUrun2;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiparisUrunGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("SiparisUrunGet handle çağrıldı");

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
        String password = "112606";
        String username = "root";
        DBconnection con = new DBconnection(url, username, password);

        List<SiparisUrun2> values = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // Query string'den siparis_id parametresini alalım
            String queryParams = exchange.getRequestURI().getQuery();
            int siparisId = Integer.parseInt(queryParams.split("=")[1]);

            // SQL Sorgusu
            String sql = "SELECT su.siparis_id, su.urun_id, su.miktar, u.urun_adi, u.fiyat " +
                    "FROM siparis_urun su " +
                    "JOIN urun u ON su.urun_id = u.urun_id " +
                    "WHERE su.siparis_id = ?";

            preparedStatement = con.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, siparisId);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                SiparisUrun2 siparisUrun = new SiparisUrun2();
                siparisUrun.setSiparis_id(rs.getInt("siparis_id"));
                siparisUrun.setUrun_id(rs.getInt("urun_id"));
                siparisUrun.setMiktar(rs.getInt("miktar"));
                siparisUrun.setUrun_adi(rs.getString("urun_adi"));
                siparisUrun.setFiyat(rs.getDouble("fiyat"));

                values.add(siparisUrun);

                System.out.println("Siparis ID: " + siparisUrun.getSiparis_id());
                System.out.println("Ürün Adı: " + siparisUrun.getUrun_adi());
                System.out.println("Fiyat: " + siparisUrun.getFiyat());
                System.out.println("Miktar: " + siparisUrun.getMiktar());
            }

        } catch (SQLException e) {
            System.out.println("SQL Hatası: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        } finally {
            con.closeConnection();
        }

        exchange.sendResponseHeaders(200, 0);
        os.write(gson.toJson(values).getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
