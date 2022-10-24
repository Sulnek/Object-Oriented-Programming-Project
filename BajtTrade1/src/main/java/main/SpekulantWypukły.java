package main;

import java.util.ArrayList;

public class SpekulantWypukły extends TypStrategii {
    @Override
    public void kupuj(Giełda giełda, Spekulant spek) {
        HistoriaGiełdy his = giełda.getHistoriaGiełdy();
        for (int i = 0; i < 4; i++) {
            double średnia1 = his.getŚredniaCena(i, 1);
            double średnia2 = his.getŚredniaCena(i, 2);
            double średnia3 = his.getŚredniaCena(i, 3);
            if (średnia1 - średnia2 > średnia2 - średnia3) {
                double cena = średnia1 * 0.9;
                int ilość;
                OfertaSpekulanta oferta;
                if (i == 0) { //jedzenie
                    ilość = (int) Math.round(spek.getZasoby().diamenty / cena);
                    int jakość = 0;
                    oferta = new OfertaSpekulanta(0, jakość, ilość, cena, spek);
                    giełda.getOfertyKupnaSpekulantów().get(i).add(oferta);
                } else {
                    for (int jakość = 1; jakość < his.getTura(); jakość++) {
                        oferta = new OfertaSpekulanta(i, jakość, 1, cena, spek);
                        giełda.getOfertyKupnaSpekulantów().get(i).add(oferta);
                    }
                }
            }
        }
    }

    @Override
    public void sprzedaj(Giełda giełda, Spekulant spek) {
        HistoriaGiełdy his = giełda.getHistoriaGiełdy();
        for (int i = 0; i < 4; i++) {
            double średnia1 = his.getŚredniaCena(i, 1);
            double średnia2 = his.getŚredniaCena(i, 2);
            double średnia3 = his.getŚredniaCena(i, 3);
            if (średnia1 - średnia2 < średnia2 - średnia3) {
                OfertaSpekulanta oferta;
                int ilość;
                int jakość;
                double cena = średnia1 * 1.1;
                if (i == 0) { //jedzenie
                    ilość = spek.getZasoby().jedzenie;
                    jakość = 0;
                    oferta = new OfertaSpekulanta(0, jakość, ilość, cena, spek);
                    giełda.getOfertySprzedażySpekulantów().get(i).add(oferta);
                    spek.getZasoby().jedzenie -= ilość;
                } else {
                    ArrayList<Integer> przedmioty = spek.getZasobyJakościowe(i);
                    for (int j = 0; j < przedmioty.size(); j++) {
                        ilość = 1;
                        jakość = przedmioty.get(j);
                        oferta = new OfertaSpekulanta(i, jakość, ilość, cena, spek);
                        giełda.getOfertySprzedażySpekulantów().get(i).add(oferta);
                        przedmioty.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "wypukły");
        return ret;
    }
}
