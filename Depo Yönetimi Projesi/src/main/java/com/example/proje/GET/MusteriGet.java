package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Musteri;
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

public class MusteriGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("MusteriGet handle çağrıldı");
        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        // CORS başlıklarını ekleyelim
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        ResultSet rs = null;
        Statement statement = null;
        String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
        String password = "112606";
        String username = "root";
        DBconnection con = new DBconnection(url, username, password);

        List<Musteri> values = new ArrayList<>();
        try {
            statement = con.createConnection().createStatement();
            String sql = "SELECT * FROM musteri";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Musteri musteri = new Musteri();
                musteri.setMusteri_id(rs.getInt("musteri_id"));
                musteri.setAd(rs.getString("ad"));
                musteri.setSoyad(rs.getString("soyad"));
                musteri.setTelefon(rs.getString("telefon"));
                musteri.setAdres(rs.getString("adres"));
                values.add(musteri);


                System.out.println(musteri.getMusteri_id());
                System.out.println(musteri.getAd());
                System.out.println(musteri.getSoyad());
                System.out.println(musteri.getTelefon());
                System.out.println(musteri.getAdres());
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
