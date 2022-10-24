package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Robotnik {
    private int id;
    private int[] poziom = new int[]{1, 1, 1, 1, 1};

    /**
     * 0 - rolnik
     * 1 - programista
     * 2 - inżynier
     * 3- rzemieślnik
     * 4 - górnik
     */
    private int kariera;
    private TypKupowania kupowanie;
    private TypProdukcji produkcja;
    private int zmiana;
    private TypNauki uczenie;
    private Zasoby zasoby;

    private Zasoby produkty = null;
    private int[] produktywność;
    private int ileBezJedzenia = 0;
    private int karaZaBrud = 0;

    public Robotnik(int id, int poziom, int kariera, TypKupowania kupowanie, TypProdukcji produkcja,
                    TypNauki uczenie, Zasoby zasoby, int[] produktywność) {
        this.id = id;
        this.kariera = kariera;
        this.kupowanie = kupowanie;
        this.produkcja = produkcja;
        this.uczenie = uczenie;
        this.zasoby = zasoby;
        this.produktywność = produktywność;
    }
    public Zasoby getZasoby() {
        return zasoby;
    }

    public boolean czyZmieniamKarierę(int tura) {
        if (zmiana == 1 && tura % 7 == 0) { //rewolucjonista
            return true;
        }
        return false;
    }

    public void zmieńKarierę(HistoriaGiełdy historia) {
        int n = id % 17;
        int idKariery = historia.najczęstszyProdukt(n, kariera);
        if (kariera == idKariery) {
            rozwijajKarierę();
        }
        else {
            kariera = idKariery;
        }
    }

    public void rozwijajKarierę() {
        poziom[kariera]++;
    }

    public int ileProdukuję(int idP) {
        int premia = 0;
        if (poziom[idP] == 1) {
            premia += 50;
        } else if (poziom[idP] == 2) {
            premia += 150;
        } else {
            premia += 300 + (poziom[idP] - 3) * 25;
        }
        if (ileBezJedzenia == 2) {
            premia -= 100;
        } else if (ileBezJedzenia == 3) {
            premia -= 300;
        }
        premia -= karaZaBrud;
        for (int i : zasoby.narzędzia) {
            premia += i;
        }
        premia += 100; //100% bazowej produkcji
        return Math.max(0, premia * produktywność[idP]);
    }

    public void produkuj(int idProduktu) {
        produkty = new Zasoby();
        int ileProdukuję = ileProdukuję(idProduktu);
        if (idProduktu == 4) {
            zasoby.diamenty += ileProdukuję;
        }
        if (idProduktu == 0) {
            produkty.jedzenie += ileProdukuję;
        }
        if (idProduktu == 1) {
            produkty.programy = new ArrayList<Integer>();
            int ileProgramówZużywam = Math.min(zasoby.programy.size(), ileProdukuję);
            for (int i = 0; i < ileProgramówZużywam; i++) {
                produkty.programy.add(zasoby.programy.get(0));
                zasoby.programy.remove(0);
            }
            for (int i = ileProgramówZużywam; i < ileProdukuję; i++) {
                produkty.programy.add(poziom[1]);
            }
        }
        if (idProduktu == 2) {
            produkty.narzędzia = new ArrayList<Integer>();
            int ileProgramówZużywam = Math.min(zasoby.programy.size(), ileProdukuję);
            for (int i = 0; i < ileProgramówZużywam; i++) {
                produkty.narzędzia.add(zasoby.programy.get(0));
                zasoby.programy.remove(0);
            }
            for (int i = ileProgramówZużywam; i < ileProdukuję; i++) {
                produkty.narzędzia.add(poziom[1]);
            }
        }
        if (idProduktu == 3) {
            produkty.ubrania = new ArrayList<Integer>();
            int ileProgramówZużywam = Math.min(zasoby.programy.size(), ileProdukuję);
            for (int i = 0; i < ileProgramówZużywam; i++) {
                produkty.ubrania.add(zasoby.programy.get(0));
                zasoby.programy.remove(0);
            }
            for (int i = ileProgramówZużywam; i < ileProdukuję; i++) {
                produkty.narzędzia.add(poziom[1]);
            }
        }
    }

    public void sprzedawaj(Giełda giełda) {
        if (getProdukty().jedzenie > 0) {
            ArrayList<OfertaSpekulanta> ofertyJ = giełda.getOfertyKupnaSpekulantów().get(0);
            for (int i = 0; i < ofertyJ.size() && getProdukty().jedzenie > 0; i++) {
                OfertaSpekulanta oferta = ofertyJ.get(i);
                if (oferta.getIlość() > getProdukty().jedzenie) {
                    getZasoby().diamenty += getProdukty().jedzenie * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty -= getProdukty().jedzenie * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[0] += getProdukty().jedzenie * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[0] += getProdukty().jedzenie;
                    getProdukty().jedzenie = 0;
                    oferta.getSpekulant().getZasoby().jedzenie += getProdukty().jedzenie;
                } else {
                    getZasoby().diamenty += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[0] += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[0] += oferta.getIlość();
                    oferta.getSpekulant().getZasoby().diamenty -= oferta.getIlość() * oferta.getCenaZaSztukę();
                    getProdukty().jedzenie -= oferta.getIlość();
                    oferta.getSpekulant().getZasoby().jedzenie += oferta.getIlość();
                    ofertyJ.remove(i);
                    i--;
                }
            }
        }

        if (getProdukty().programy != null) {
            sprzedajProduktJakościowy(getProdukty().programy, giełda, 1);
        }
        if (getProdukty().narzędzia != null) {
            sprzedajProduktJakościowy(getProdukty().narzędzia, giełda, 2);
        }
        if (getProdukty().ubrania != null) {
            sprzedajProduktJakościowy(getProdukty().ubrania, giełda, 3);
        }
    }

    public void sprzedajProduktJakościowy(ArrayList<Integer> produkty, Giełda giełda, int idP) {
        ArrayList<OfertaSpekulanta> oferty = giełda.getOfertyKupnaSpekulantów().get(idP);
        int indeksP = 0, indeksO = 0;
        while (indeksO < oferty.size() && indeksP < produkty.size()) {
            OfertaSpekulanta oferta = oferty.get(indeksO);
            if (oferta.getJakość() < produkty.get(indeksP)) {
                indeksO++;
            } else if (oferta.getJakość() > produkty.get(indeksP)) {
                indeksP++;
            } else {
                getZasoby().diamenty += oferta.getCenaZaSztukę();
                oferta.getSpekulant().getZasobyJakościowe(idP).add(oferta.getJakość());
                oferta.getSpekulant().getZasoby().diamenty -= oferta.getCenaZaSztukę();
                giełda.obrótPieniężnyProduktuDziś[idP] += oferta.getCenaZaSztukę();
                giełda.ilośćProduktuDziś[idP]++;
                oferta.setIlość(oferta.getIlość() - 1);
                if (oferta.getIlość() == 0) {
                    oferty.remove(indeksO);
                }
            }
        }
    }

    public void kupuj(Giełda giełda) {
        kupowanie.kupuj(this, giełda);
    }

    public void jedz() {
        if (ileBezJedzenia == 0) { //już dzisiaj jadł
            return;
        }
        if (zasoby.jedzenie < 100) {
            ileBezJedzenia++;
        }
        zasoby.jedzenie = Math.max(zasoby.jedzenie - 100, 0);
    }

    public void ubierajSię(int karaZaBrakUbrań) {
        if ((zasoby.ubrania).size() < 100) {
            karaZaBrud = karaZaBrakUbrań;
        }
        int ileNoszęUbrań = Math.min(100, zasoby.ubrania.size());
        for (int i = 0; i < ileNoszęUbrań; i++) {
            zasoby.ubrania.set(i, zasoby.ubrania.get(i) - 1);
            if (zasoby.ubrania.get(i) == 0) {
                zasoby.ubrania.remove(i);
                i--;
                ileNoszęUbrań--;
            }
        }
    }

    public int getIleBezJedzenia() {
        return ileBezJedzenia;
    }

    public void setIleBezJedzenia(int n) {
        ileBezJedzenia = n;
    }
    public TypNauki getUczenie() {
        return uczenie;
    }

    public TypProdukcji getProdukcja() {
        return produkcja;
    }

    public Zasoby getProdukty() {
        return produkty;
    }

    public int getId() {
        return id;
    }

    public void zostańWykupiony() {
        //ewentualne zwiększenie zasoby.diamenty
        produkty.ubrania = null;
        produkty.programy = null;
        produkty.narzędzia = null;
        produkty.jedzenie = 0;
    }

    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("id", id);
        ret.put("poziom", poziom[kariera]);
        if (kariera == 0) {
            ret.put("kariera", "rolnik");
        } else if (kariera == 1) {
            ret.put("kariera", "programista");
        } else if (kariera == 2) {
            ret.put("kariera", "inżynier");
        } else if (kariera == 3) {
            ret.put("kariera", "rzemieślnik");
        } else if (kariera == 4) {
            ret.put("kariera", "górnik");
        }
        ret.put("kupowanie", kupowanie.toJSONObject());
        ret.put("produkcja", produkcja.toJSONObject());
        ret.put("uczenie", uczenie.toJSONObject());

        if (zmiana == 0) {
            ret.put("zmiana", "konserwatysta");
        } else {
            ret.put("zmiana", "rewolucjonista");
        }

        JSONObject prod = new JSONObject();
        prod.put("jedzenie", produktywność[0]);
        prod.put("programy", produktywność[1]);
        prod.put("narzędzia", produktywność[2]);
        prod.put("ubrania", produktywność[3]);
        prod.put("diamenty", produktywność[4]);
        ret.put("produktywność", prod);

        ret.put("zasoby", zasoby.toJSONObject());

        return ret;
    }


}

class ZamożnośćRobotnikaNierosnąco implements Comparator<Robotnik> {
    public int compare(Robotnik a, Robotnik b)
    {
        if (a.getZasoby().diamenty != b.getZasoby().diamenty) {
            return b.getZasoby().diamenty - a.getZasoby().diamenty;
        }
        return a.getId() - b.getId();
    }
}