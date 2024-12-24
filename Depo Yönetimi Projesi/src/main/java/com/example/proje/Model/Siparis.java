package com.example.proje.Model;

import java.util.Date;

public class Siparis {
    private int siparis_id;
    private Date siparis_tarihi;
    private int musteri_id;
    private String odeme_durumu;

    public int getSiparis_id(){
        return siparis_id;
    }
    public void setSiparis_id(int siparis_id){
        this.siparis_id=siparis_id;
    }

    public Date getSiparis_tarihi() {
        return siparis_tarihi;
    }

    public void setSiparis_tarihi(Date siparis_tarihi) {
        this.siparis_tarihi = siparis_tarihi;
    }

    public int getMusteri_id() {
        return musteri_id;
    }

    public void setMusteri_id(int musteri_id) {
        this.musteri_id = musteri_id;
    }
    public String getOdeme_durumu(){
        return odeme_durumu;
    }

    public void setOdeme_durumu(String odeme_durumu) {
        this.odeme_durumu = odeme_durumu;
    }
}
