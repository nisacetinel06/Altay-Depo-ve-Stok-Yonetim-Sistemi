package com.example.proje.Model;

public class Musteri {
    private int musteri_id;
    private String ad;
    private String soyad;
    private String telefon;
    private String adres;

    public int getMusteri_id(){
        return musteri_id;
    }
    public void setMusteri_id(int musteri_id){
        this.musteri_id=musteri_id;
    }
    public String getAd(){
        return ad;
    }
    public void setAd(String ad){
        this.ad=ad;
    }
    public String getSoyad(){
        return soyad;
    }
    public void setSoyad(String soyad){
        this.soyad=soyad;
    }
    public String getTelefon(){
        return telefon;
    }
    public void setTelefon(String telefon){
        this.telefon=telefon;
    }
    public String getAdres(){
        return adres;
    }
    public void setAdres(String adres){
        this.adres=adres;
    }
}
