package main;

import java.util.ArrayList;

public class Zmechanizowany extends Czyścioszek {
    private int liczbaNarzędzi;
    @Override
    public void kupuj(Robotnik rob, Giełda giełda) {
        //2 to id narzędzi

        super.kupuj(rob, giełda);
        int ileNarzędziKupić = liczbaNarzędzi;
        ArrayList<OfertaSpekulanta> ofertyN = giełda.getOfertySprzedażySpekulantów().get(2);
        for (int i = 0; i < ofertyN.size() && ileNarzędziKupić > 0; i++) {
            OfertaSpekulanta oferta = ofertyN.get(i);
            if (Math.min(oferta.getIlość(), ileNarzędziKupić) * oferta.getCenaZaSztukę() <=
                    rob.getZasoby().diamenty) { //jeśli mamy wystarczające fundusze
                if (oferta.getIlość() >= ileNarzędziKupić) {
                    oferta.setIlość(oferta.getIlość() - ileNarzędziKupić);
                    ofertyN.set(i, oferta);
                    rob.getZasoby().diamenty -= ileNarzędziKupić * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += ileNarzędziKupić * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[2] += ileNarzędziKupić * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[2] += ileNarzędziKupić;
                    for (int j = 0; j < ileNarzędziKupić; j++) {
                        rob.getZasoby().narzędzia.add(oferta.getJakość());
                    }
                    ileNarzędziKupić = 0;
                } else {
                    ileNarzędziKupić -= oferta.getIlość();
                    for (int j = 0; j < oferta.getIlość(); j++) {
                        rob.getZasoby().narzędzia.add(oferta.getJakość());
                    }
                    rob.getZasoby().diamenty -= oferta.getIlość() * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[2] += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[2] += oferta.getIlość();
                    ofertyN.remove(i);
                    i--;
                }
            } else {
                ileNarzędziKupić = 0; //nic już nie kupimy
            }
        }
        giełda.setOfertySprzedażySpekulantów(2, ofertyN);
    }

    public Zmechanizowany(int ln) {
        liczbaNarzędzi = ln;
    }

    public int getLiczbaNarzędzi() {
        return liczbaNarzędzi;
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "zmechanizowany");
        ret.put("liczba_narzędzi", liczbaNarzędzi);
        return ret;
    }
}
