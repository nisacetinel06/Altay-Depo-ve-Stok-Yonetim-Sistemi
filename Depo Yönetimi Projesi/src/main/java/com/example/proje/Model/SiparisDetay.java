package com.example.proje.Model;

import java.sql.Date;

public class SiparisDetay {
    private int siparis_id;
    private Date siparis_tarihi;
    private String musteri_adi;
    private String musteri_soyadi;

    public int getSiparis_id() {
        return siparis_id;
    }

    public void setSiparis_id(int siparis_id) {
        this.siparis_id = siparis_id;
    }

    public Date getSiparis_tarihi() {
        return siparis_tarihi;
    }

    public void setSiparis_tarihi(Date siparis_tarihi) {
        this.siparis_tarihi = siparis_tarihi;
    }

    public String getMusteri_adi() {
        return musteri_adi;
    }

    public void setMusteri_adi(String musteri_adi) {
        this.musteri_adi = musteri_adi;
    }

    public String getMusteri_soyadi() {
        return musteri_soyadi;
    }

    public void setMusteri_soyadi(String musteri_soyadi) {
        this.musteri_soyadi = musteri_soyadi;
    }
}
