package com.example.proje.Connection;

import com.example.proje.DELETE.CalisanDelete;
import com.example.proje.DELETE.UrunDelete;
import com.example.proje.GET.*;
import com.example.proje.POST.CalisanPost;
import com.example.proje.POST.OdemePost;
import com.example.proje.POST.SiparisUrunPost;
import com.example.proje.POST.UrunPost;
import com.example.proje.PUT.CalisanPut;
import com.example.proje.PUT.UrunPut;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class httpService {
    public void createServer() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8090), 0);
        server.setExecutor(null);

        server.createContext("/GET/CalisanGet",new CalisanGet());
        server.createContext("/GET/UrunGet",new UrunGet());
        server.createContext("/GET/UrunKategorisiGet",new UrunKategorisiGet());
        server.createContext("/GET/SiparisGet",new SiparisGet());
        server.createContext("/GET/IadeGet",new IadeGet());
        server.createContext("/GET/SiparisUrunGet",new SiparisUrunGet());
        server.createContext("/GET/SiparisDetayGet",new SiparisDetayGet());
        server.createContext("/siparis_urun", new SiparisUrunGet());
        server.createContext("/urunler/kategori",new KategoriyeGoreUrunGet());
        server.createContext("/GET/DepoTedarikciRaporuGet",new DepoTedarikciRaporuGet());
        server.createContext("/GET/MusteriGet", new MusteriGet());

        server.createContext("/DELETE/CalisanDelete",new CalisanDelete());
        server.createContext("/DELETE/UrunDelete",new UrunDelete());

        server.createContext("/POST/CalisanPost",new CalisanPost());
        server.createContext("/POST/UrunPost",new UrunPost());
        server.createContext("/POST/SiparisUrunPost",new SiparisUrunPost());
        server.createContext("/POST/OdemePost",new OdemePost());

        server.createContext("/PUT/CalisanPut",new CalisanPut());
        server.createContext("/PUT/UrunPut",new UrunPut());

        server.start();
        System.out.println("Server started at " + new InetSocketAddress(8090));


    }
}
