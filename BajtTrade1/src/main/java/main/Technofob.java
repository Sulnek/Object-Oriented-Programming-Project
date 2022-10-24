package main;

import java.util.ArrayList;

public class Technofob extends TypKupowania {
    @Override
    public void kupuj(Robotnik rob, Giełda giełda) {
        //0 to id jedzenia
        int jedzenieDoKupienia = 100;
        ArrayList<OfertaSpekulanta> ofertyS = giełda.getOfertySprzedażySpekulantów().get(0);
        for (int i = 0; i < ofertyS.size() && jedzenieDoKupienia > 0; i++) {
            OfertaSpekulanta oferta = ofertyS.get(i);
            if (Math.min(oferta.getIlość(), jedzenieDoKupienia) * oferta.getCenaZaSztukę() <=
                        rob.getZasoby().diamenty) { //jeśli mamy wystarczające fundusze
                if (oferta.getIlość() > jedzenieDoKupienia) {
                    oferta.setIlość(oferta.getIlość() - jedzenieDoKupienia);
                    ofertyS.set(i, oferta);
                    rob.getZasoby().diamenty -= jedzenieDoKupienia * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += jedzenieDoKupienia * oferta.getCenaZaSztukę();
                    rob.getZasoby().jedzenie += jedzenieDoKupienia;
                    giełda.obrótPieniężnyProduktuDziś[0] += jedzenieDoKupienia * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[0] += jedzenieDoKupienia;
                    jedzenieDoKupienia = 0;
                } else {
                    jedzenieDoKupienia -= oferta.getIlość();
                    rob.getZasoby().jedzenie += oferta.getIlość();
                    rob.getZasoby().diamenty -= oferta.getIlość() * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[0] += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[0] += oferta.getIlość();
                    ofertyS.remove(i);
                    i--;
                }
            } else {
                jedzenieDoKupienia = 0; //nic już nie kupimy
            }
        }
        giełda.setOfertySprzedażySpekulantów(0, ofertyS);
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "technofob");
        return ret;
    }
}
