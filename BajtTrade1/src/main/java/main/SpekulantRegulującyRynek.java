package main;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class SpekulantRegulującyRynek extends TypStrategii {
    @Override
    public void kupuj(Giełda giełda, Spekulant spek) {
        HistoriaGiełdy his = giełda.getHistoriaGiełdy();
        for (int i = 0; i < 4; i++) {
            OfertaSpekulanta oferta;
            int ilość;
            double cena = (his.getŚredniaCena(i, 1) * his.getIleSprzedano(i, 1));
            cena /= his.maksymalnaLiczbaProduktu(i);
            cena *= 0.9;
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

    @Override
    public void sprzedaj(Giełda giełda, Spekulant spek) {
        HistoriaGiełdy his = giełda.getHistoriaGiełdy();
        for (int i = 0; i < 4; i++) {
            OfertaSpekulanta oferta;
            int ilość;
            int jakość;
            double cena = (his.getŚredniaCena(i, 1) * his.getIleSprzedano(i, 1));
            cena /= his.maksymalnaLiczbaProduktu(i);
            cena *= 1.1;
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

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "regulujący rynek");
        return ret;
    }
}
