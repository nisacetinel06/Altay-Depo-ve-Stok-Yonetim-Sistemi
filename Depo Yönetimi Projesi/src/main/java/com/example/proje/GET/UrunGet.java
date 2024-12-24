package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Urun;
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

public class UrunGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("UrunGet handle çağrıldı");
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

        List<Urun> values = new ArrayList<>();
        try {
            statement = con.createConnection().createStatement();
            String sql = "SELECT * FROM urun";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Urun urun = new Urun();
                urun.setUrun_id(rs.getInt("urun_id"));
                urun.setUrun_adi(rs.getString("urun_adi"));
                urun.setStok_miktari(rs.getInt("stok_miktari"));
                urun.setFiyat(rs.getDouble("fiyat"));
                urun.setKategori_id(rs.getInt("kategori_id"));
                values.add(urun);

                System.out.println(urun.getUrun_id());
                System.out.println(urun.getUrun_adi());
                System.out.println(urun.getStok_miktari());
                System.out.println(urun.getFiyat());
                System.out.println(urun.getKategori_id());
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
