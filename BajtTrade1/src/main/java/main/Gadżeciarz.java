package main;

import java.util.ArrayList;

public class Gadżeciarz extends Zmechanizowany {
    @Override
    public void kupuj(Robotnik rob, Giełda giełda) {
        //1 to id programów

        super.kupuj(rob, giełda);
        Zasoby produkty = rob.getProdukty();
        int ileProgramówKupić = Math.max(produkty.diamenty, produkty.jedzenie);
        if (produkty.narzędzia != null) {
            ileProgramówKupić = Math.max(ileProgramówKupić, produkty.narzędzia.size());
        } else if (produkty.programy != null) {
            ileProgramówKupić = Math.max(ileProgramówKupić, produkty.programy.size());
        } else if (produkty.ubrania != null) {
            ileProgramówKupić = Math.max(ileProgramówKupić, produkty.ubrania.size());
        }

        ArrayList<OfertaSpekulanta> ofertyP = giełda.getOfertySprzedażySpekulantów().get(1);
        for (int i = 0; i < ofertyP.size() && ileProgramówKupić > 0; i++) {
            OfertaSpekulanta oferta = ofertyP.get(i);
            if (Math.min(oferta.getIlość(), ileProgramówKupić) * oferta.getCenaZaSztukę() <=
                    rob.getZasoby().diamenty) { //jeśli mamy wystarczające fundusze
                if (oferta.getIlość() >= ileProgramówKupić) {
                    oferta.setIlość(oferta.getIlość() - ileProgramówKupić);
                    ofertyP.set(i, oferta);
                    rob.getZasoby().diamenty -= ileProgramówKupić * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += ileProgramówKupić * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[1] += ileProgramówKupić * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[1] += ileProgramówKupić;
                    for (int j = 0; j < ileProgramówKupić; j++) {
                        rob.getZasoby().programy.add(oferta.getJakość());
                    }
                    ileProgramówKupić = 0;
                } else {
                    ileProgramówKupić -= oferta.getIlość();
                    for (int j = 0; j < oferta.getIlość(); j++) {
                        rob.getZasoby().programy.add(oferta.getJakość());
                    }
                    rob.getZasoby().diamenty -= oferta.getIlość() * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[1] += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[1] += oferta.getIlość();
                    ofertyP.remove(i);
                    i--;
                }
            } else {
                ileProgramówKupić = 0; //nic już nie kupimy
            }
        }
        giełda.setOfertySprzedażySpekulantów(1, ofertyP);
    }

    public Gadżeciarz(int ln) {
        super(ln);
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "gadżeciarz");
        ret.put("liczba_narzędzi", super.getLiczbaNarzędzi());
        return ret;
    }
}
