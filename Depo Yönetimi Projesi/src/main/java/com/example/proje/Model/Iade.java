package com.example.proje.Model;

import java.util.Date;

public class Iade {
    private int iade_id;
    private int siparis_id;
    private String iade_nedeni;
    private Date iade_tarihi;

    public int getIadeId(){
        return iade_id;
    }
    public void setIadeId(int iade_id) {
        this.iade_id= iade_id;
    }

    public int getSiparisId() {
        return siparis_id;
    }

    public void setSiparisId( int siparis_id) {
        this.siparis_id = siparis_id;
    }

    public String getIadeNedeni() {
        return iade_nedeni;
    }

    public void setIadeNedeni(String iade_nedeni) {
        this.iade_nedeni= iade_nedeni;
    }
    public Date getIadeTarihi() {
        return iade_tarihi;
    }

    public void setIadeTarihi(Date iade_tarihi) {
        this.iade_tarihi = iade_tarihi;
    }

}
