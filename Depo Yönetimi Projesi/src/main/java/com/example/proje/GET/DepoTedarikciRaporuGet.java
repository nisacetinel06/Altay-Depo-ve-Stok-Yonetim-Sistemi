package com.example.proje.GET;

import com.example.proje.Connection.DBconnection;
import com.example.proje.Model.Depo;
import com.example.proje.Model.DepoTedarikciRaporu;
import com.example.proje.Model.Tedarikci;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepoTedarikciRaporuGet implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("DepoTedarikciRaporuGet handle çağrıldı");


        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        OutputStream os = exchange.getResponseBody();
        Gson gson = new Gson();

        String url = "jdbc:mysql://localhost:3306/depo_yonetimi";
        String password = "112606";
        String username = "root";
        DBconnection con = new DBconnection(url, username, password);

        List<DepoTedarikciRaporu> raporListesi = new ArrayList<>();
        CallableStatement callableStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            // İlk olarak saklı prosedürü çalıştır
            callableStatement = con.createConnection().prepareCall("{CALL DepoTedarikciRaporu()}");
            callableStatement.execute();  // Saklı prosedür çalıştırılacak

            // Daha sonra SELECT komutuyla depo_tedarikci_rapor tablosundaki verileri al
            statement = con.createConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM depo_tedarikci_rapor");

            while (rs.next()) {
                DepoTedarikciRaporu rapor = new DepoTedarikciRaporu();
                Depo depo = new Depo();
                Tedarikci tedarikci = new Tedarikci();


                depo.setDepoAdi(rs.getString("depo_adi"));
                tedarikci.setTedarikci_adi(rs.getString("tedarikci_adi"));
                tedarikci.setTelefon(rs.getString("telefon"));

                rapor.setDepo(depo);
                rapor.setTedarikci(tedarikci);
                raporListesi.add(rapor);


                System.out.println(rapor);
            }

        } catch (SQLException e) {
            System.out.println("SQL Hatası: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        } finally {
            con.closeConnection();
        }


        exchange.sendResponseHeaders(200, 0);
        os.write(gson.toJson(raporListesi).getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
