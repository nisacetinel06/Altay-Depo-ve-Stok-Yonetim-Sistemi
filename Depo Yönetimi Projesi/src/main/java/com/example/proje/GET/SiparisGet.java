package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Siparis;
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

public class SiparisGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("SiparisGet handle çağrıldı");
        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*"); // Tüm kaynaklardan gelen isteklere izin ver
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE"); // İzin verilen HTTP yöntemlerini belirt
        exchange.getResponseHeaders().set("Content-Type", "application/json"); // JSON formatında yanıt döndür


        ResultSet rs = null;
        Statement statement = null;
        String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
        String password = "112606";
        String username = "root";
        DBconnection con = new DBconnection(url, username, password);

        List<Siparis> values = new ArrayList<>();
        try {
            statement = con.createConnection().createStatement();
            String sql = "SELECT * FROM siparis";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Siparis siparis = new Siparis();
                siparis.setSiparis_id(rs.getInt("siparis_id"));
                siparis.setSiparis_tarihi(rs.getDate("siparis_tarihi"));
                siparis.setMusteri_id(rs.getInt("musteri_id"));
                siparis.setOdeme_durumu(rs.getString("odeme_durumu"));
                values.add(siparis);

                System.out.println(siparis.getSiparis_id());
                System.out.println(siparis.getSiparis_tarihi());
                System.out.println(siparis.getMusteri_id());
                System.out.println(siparis.getOdeme_durumu());
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
