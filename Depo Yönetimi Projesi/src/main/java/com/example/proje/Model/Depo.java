package com.example.proje.Model;

public class Depo {
    private int depo_id;
    private String depo_adi;
    private String konum;

    public int getDepoId(){
        return depo_id;
    }

    public void setDepoId(int depo_id) {
        this.depo_id= depo_id;
    }

    public String getDepoAdi() {
        return depo_adi;
    }

    public void setDepoAdi(String depo_adi) {
        this.depo_adi = depo_adi;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }
}
