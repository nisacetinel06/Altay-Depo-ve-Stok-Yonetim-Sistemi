package com.example.proje.Model;

public class DepoTedarikciRaporu {

    private Depo depo;
    private Tedarikci tedarikci;


    public Depo getDepo() {
        return depo;
    }

    public void setDepo(Depo depo) {
        this.depo = depo;
    }

    public Tedarikci getTedarikci() {
        return tedarikci;
    }

    public void setTedarikci(Tedarikci tedarikci) {
        this.tedarikci = tedarikci;
    }

    @Override
    public String toString() {
        return "DepoTedarikciRaporu{" +
                "depo=" + depo +
                ", tedarikci=" + tedarikci +
                '}';
    }
}
