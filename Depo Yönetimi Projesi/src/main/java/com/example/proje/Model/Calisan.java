package com.example.proje.Model;

public class Calisan {
    private int calisan_id; // calisan_id
    private String calisan_adi; // calisan_adi
    private String pozisyon; // pozisyon
    private int depo_id; // depo_id

    // Getter ve Setter metodları
    public int getCalisanId() {
        return calisan_id;
    }

    public void setCalisanId(int calisan_id) {
        this.calisan_id = calisan_id;
    }

    public String getCalisanAdi() {
        return calisan_adi;
    }

    public void setCalisanAdi(String calisan_adi) {
        this.calisan_adi = calisan_adi;
    }

    public String getPozisyon() {
        return pozisyon;
    }

    public void setPozisyon(String pozisyon) {
        this.pozisyon = pozisyon;
    }

    public int getDepoId() {
        return depo_id;
    }

    public void setDepoId(int depo_id) {
        this.depo_id = depo_id;
    }


    @Override
    public String toString() {
        return "calisan{" +
                "calisanId=" + calisan_id +
                ", calisanAdi='" + calisan_adi + '\'' +
                ", pozisyon='" + pozisyon + '\'' +
                ", depoId=" + depo_id +
                '}';
    }
}
