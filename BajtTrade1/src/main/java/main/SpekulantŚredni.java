package main;

import java.util.ArrayList;

public class SpekulantŚredni extends TypStrategii {
    private int historiaSpekulantaŚredniego;

    public SpekulantŚredni(int his) {
        historiaSpekulantaŚredniego = his;
    }

    @Override
    public void kupuj(Giełda giełda, Spekulant spek) {
        HistoriaGiełdy his = giełda.getHistoriaGiełdy();
        for (int i = 0; i < 4; i++) {
            int ilość;
            double cena;
            OfertaSpekulanta oferta;
            if (i == 0) { //jedzenie
                if (spek.getZasoby().jedzenie == 0) {
                    cena = his.średniaCena(i, historiaSpekulantaŚredniego) * 0.95;
                } else {
                    cena = his.średniaCena(i, historiaSpekulantaŚredniego) * 0.9;
                }
                ilość = (int) Math.round(spek.getZasoby().diamenty / cena);
                int jakość = 0;
                oferta = new OfertaSpekulanta(0, jakość, ilość, cena, spek);
                giełda.getOfertyKupnaSpekulantów().get(i).add(oferta);
            } else {
                if (spek.getZasobyJakościowe(i) == null || spek.getZasobyJakościowe(i).size() == 0) {
                    cena = his.średniaCena(i, historiaSpekulantaŚredniego) * 0.95;
                } else {
                    cena = his.średniaCena(i, historiaSpekulantaŚredniego) * 0.9;
                }
                for (int jakość = 1; jakość < his.getTura(); jakość++) {
                    oferta = new OfertaSpekulanta(i, jakość, 1, cena, spek);
                    giełda.getOfertyKupnaSpekulantów().get(i).add(oferta);
                }
            }
        }
    }

    @Override
    public void sprzedaj(Giełda giełda, Spekulant spek) {
        HistoriaGiełdy his = giełda.getHistoriaGiełdy();
        for (int i = 0; i < 4; i++) {
            double cena = his.średniaCena(i, historiaSpekulantaŚredniego) * 1.1;
            int ilość, jakość;
            if (i == 0) { //jedzenie
                ilość = spek.getZasoby().jedzenie;
                jakość = 0;
                OfertaSpekulanta oferta = new OfertaSpekulanta(0, jakość, ilość, cena, spek);
                giełda.getOfertySprzedażySpekulantów().get(i).add(oferta);
                spek.getZasoby().jedzenie -= ilość;
            } else {
                ArrayList<Integer> przedmioty = spek.getZasobyJakościowe(i);
                for (int j = 0; j < przedmioty.size(); j++) {
                    ilość = 1;
                    jakość = przedmioty.get(j);
                    OfertaSpekulanta oferta = new OfertaSpekulanta(i, jakość, ilość, cena, spek);
                    giełda.getOfertySprzedażySpekulantów().get(i).add(oferta);
                    przedmioty.remove(j);
                    j--;
                }
            }
        }
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "średni");
        return ret;
    }
}
