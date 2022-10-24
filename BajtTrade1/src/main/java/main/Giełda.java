package main;

import java.util.ArrayList;

public class Giełda {
    private int typGiełdy;

    public double[] ilośćProduktuDziś;

    public double[] obrótPieniężnyProduktuDziś;

    private HistoriaGiełdy historiaGiełdy;

    private ArrayList<ArrayList<OfertaSpekulanta>> ofertySprzedażySpekulantów = null;

    private ArrayList<ArrayList<OfertaSpekulanta>> ofertyKupnaSpekulantów = null;

    public Giełda(int typ, Ceny ceny) {
        typGiełdy = typ;
        ilośćProduktuDziś = new double[4];
        obrótPieniężnyProduktuDziś = new double[4];
        historiaGiełdy = new HistoriaGiełdy(ceny);
    }

    public HistoriaGiełdy getHistoriaGiełdy() {
        return historiaGiełdy;
    }

    public boolean czyRuchKapitalistyczny(int tura) {
        if (typGiełdy == 0) {
            return true;
        }
        if (typGiełdy == 2) {
            return false;
        }
        if (tura % 2 == 1) {
            return false;
        }
        return true;
    }

    public ArrayList<ArrayList<OfertaSpekulanta>> getOfertySprzedażySpekulantów() {
        return ofertySprzedażySpekulantów;
    }

    public void setOfertySprzedażySpekulantów(int i, ArrayList<OfertaSpekulanta> oferty) {
        ofertySprzedażySpekulantów.set(i, oferty);
    }

    public ArrayList<ArrayList<OfertaSpekulanta>> getOfertyKupnaSpekulantów() {
        return ofertyKupnaSpekulantów;
    }

    public void zerujOfertyKupna() {
        ofertyKupnaSpekulantów = new ArrayList<ArrayList<OfertaSpekulanta>>();
        for (int i = 0; i < 4; i++) {
            ofertyKupnaSpekulantów.add(new ArrayList<OfertaSpekulanta>());
        }
    }

    public void zerujOfertySprzedaży() {
        ofertySprzedażySpekulantów = new ArrayList<ArrayList<OfertaSpekulanta>>();
        for (int i = 0; i < 4; i++) {
            ofertySprzedażySpekulantów.add(new ArrayList<OfertaSpekulanta>());
        }
    }
}
