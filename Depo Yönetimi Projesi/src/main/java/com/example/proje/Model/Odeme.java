package com.example.proje.Model;

public class Odeme {
    private int odeme_id;
    private double miktar;
    private String odeme_tarihi;
    private String odeme_tipi;
    private int siparis_id;

    public int getOdeme_id(){
        return odeme_id;
    }
    public void setOdeme_id(int odeme_id){
        this.odeme_id=odeme_id;
    }
    public double getMiktar(){
        return miktar;
    }
    public void setMiktar(double miktar){
        this.miktar=miktar;
    }
    public String getOdeme_tarihi(){
        return odeme_tarihi;
    }
    public void setOdeme_tarihi(String odeme_tarihi){
        this.odeme_tarihi=odeme_tarihi;
    }
    public String getOdeme_tipi(){
        return odeme_tipi;
    }
    public void setOdeme_tipi(String odeme_tipi){
        this.odeme_tipi=odeme_tipi;
    }
    public int getSiparis_id(){
        return siparis_id;
    }
    public void setSiparis_id(int siparis_id){
        this.siparis_id=siparis_id;
    }
}
