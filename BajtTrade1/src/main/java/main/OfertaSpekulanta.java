package main;

import java.util.Comparator;

public class OfertaSpekulanta {
    private int idProduktu;
    private int jakość;
    private int ilość;
    private double cenaZaSztukę;
    private Spekulant spekulant;

    public int getIdProduktu() {
        return idProduktu;
    }

    public int getJakość() {
        return jakość;
    }

    public int getIlość() {
        return ilość;
    }

    public void setIlość(int val) {
        ilość = val;
    }

    public double getCenaZaSztukę() {
        return cenaZaSztukę;
    }

    public OfertaSpekulanta(int idP, int jakość, int ilość,
                            double cenaZaSztukę, Spekulant spek) {
        idProduktu = idP;
        this.jakość = jakość;
        this.ilość = ilość;
        this.cenaZaSztukę = cenaZaSztukę;
        this.spekulant = spek;
    }

    public Spekulant getSpekulant() {
        return spekulant;
    }
}

class AtrakcyjnośćOferty implements Comparator<OfertaSpekulanta> {
    public int compare(OfertaSpekulanta a, OfertaSpekulanta b)
    {
        if (a.getJakość() != b.getJakość()) {
            return b.getJakość() - a.getJakość();
        }
        if (a.getCenaZaSztukę() - b.getCenaZaSztukę() < 0) {
            return -1;
        } else if (a.getCenaZaSztukę() - b.getCenaZaSztukę() > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}