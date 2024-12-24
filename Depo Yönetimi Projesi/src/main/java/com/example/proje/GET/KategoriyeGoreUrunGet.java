package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Urun;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriyeGoreUrunGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("KategoriyeGoreUrunGet handle çağrıldı");


        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        String url = "jdbc:mysql://localhost:3306/depo_yonetimi"; // Veritabanı URL
        String password = "112606"; // Veritabanı şifresi
        String username = "root"; // Veritabanı kullanıcı adı
        DBconnection con = new DBconnection(url, username, password);

        List<Urun> values = new ArrayList<>();
        CallableStatement callableStatement = null;
        ResultSet rs = null;

        try {

            String queryParams = exchange.getRequestURI().getQuery();
            int kategoriId = Integer.parseInt(queryParams.split("=")[1]);


            callableStatement = con.createConnection().prepareCall("{CALL kategoriye_gore_urun_listele(?)}");
            callableStatement.setInt(1, kategoriId);
            rs = callableStatement.executeQuery();

            while (rs.next()) {
                Urun urun = new Urun();
                urun.setUrun_adi(rs.getString("urun_adi"));
                urun.setFiyat(rs.getDouble("fiyat"));
                urun.setStok_miktari(rs.getInt("stok_miktari"));
                values.add(urun);

                System.out.println(urun.getUrun_adi());
                System.out.println(urun.getFiyat());
                System.out.println(urun.getStok_miktari());
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
