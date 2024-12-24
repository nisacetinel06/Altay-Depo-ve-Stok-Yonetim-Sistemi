package com.example.proje.Model;

public class Tedarikci {
    private int tedarikci_id;
    private String tedarikci_adi;
    private String tedarikci_adresi;
    private String telefon;

    public int getTedarikci_id() {
        return tedarikci_id;
    }
    public void setTedarikci_id(int tedarikci_id) {
        this.tedarikci_id = tedarikci_id;
    }
    public String getTedarikci_adi(){
        return tedarikci_adi;
    }

    public void setTedarikci_adi(String tedarikci_adi) {
        this.tedarikci_adi = tedarikci_adi;
    }
    public String getTedarikci_adresi(){
        return tedarikci_adresi;
    }
    public void setTedarikci_adresi(String tedarikci_adresi){
        this.tedarikci_adresi=tedarikci_adresi;
    }
    public String getTelefon(){
        return telefon;
    }
    public void setTelefon(String telefon){
        this.telefon=telefon;
    }
}
