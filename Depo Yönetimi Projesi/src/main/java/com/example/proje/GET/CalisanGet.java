package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Calisan;
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

public class CalisanGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("CalisanGet handle çağrıldı");
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

        List<Calisan> values = new ArrayList<>();
        try {
            statement = con.createConnection().createStatement();
            String sql = "SELECT * FROM calisan";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Calisan calisan = new Calisan();
                calisan.setCalisanId(rs.getInt("calisan_id"));
                calisan.setCalisanAdi(rs.getString("calisan_adi"));
                calisan.setPozisyon(rs.getString("pozisyon"));
                calisan.setDepoId(rs.getInt("depo_id"));
                values.add(calisan);


                System.out.println(calisan.getCalisanId());
                System.out.println(calisan.getCalisanAdi());
                System.out.println(calisan.getPozisyon());
                System.out.println(calisan.getDepoId());
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
