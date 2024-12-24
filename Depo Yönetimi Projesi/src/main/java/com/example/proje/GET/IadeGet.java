package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IadeGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("IadeGet handle çağrıldı");
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

        // Iade nedenine göre iade sayısını hesaplamak için bir liste
        List<Map<String, Object>> iadeList = new ArrayList<>();
        try {
            statement = con.createConnection().createStatement();
            String sql = "SELECT iade_nedeni, COUNT(*) AS iade_sayisi FROM iade GROUP BY iade_nedeni";
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                Map<String, Object> iadeData = new HashMap<>();
                iadeData.put("iade_nedeni", rs.getString("iade_nedeni"));
                iadeData.put("iade_sayisi", rs.getInt("iade_sayisi"));
                iadeList.add(iadeData);

                System.out.println("İade Nedeni: " + rs.getString("iade_nedeni"));
                System.out.println("İade Sayısı: " + rs.getInt("iade_sayisi"));
            }

        } catch (SQLException e) {
            System.out.println("SQL Hatası: " + e.getMessage());
        } finally {
            con.closeConnection();
        }

        exchange.sendResponseHeaders(200, 0);
        os.write(gson.toJson(iadeList).getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
