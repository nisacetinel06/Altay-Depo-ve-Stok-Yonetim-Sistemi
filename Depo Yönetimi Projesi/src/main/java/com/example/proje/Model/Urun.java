package com.example.proje.Model;

public class Urun {
    private int urun_id;
    private String urun_adi;
    private int stok_miktari;
    private double fiyat;
    private int kategori_id;

    public int getUrun_id(){
        return urun_id;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }
    public String getUrun_adi(){
        return urun_adi;
    }
    public void setUrun_adi(String urun_adi){
        this.urun_adi=urun_adi;
    }
    public int getStok_miktari(){
        return stok_miktari;
    }
    public void setStok_miktari(int stok_miktari){
        this.stok_miktari=stok_miktari;
    }
    public double getFiyat(){
        return fiyat;
    }
    public void setFiyat(double fiyat){
        this.fiyat=fiyat;
    }
    public int getKategori_id(){
        return kategori_id;
    }
    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }
}







