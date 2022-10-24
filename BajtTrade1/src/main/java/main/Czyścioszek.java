package main;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Czyścioszek extends Technofob {
    @Override
    public void kupuj(Robotnik rob, Giełda giełda) {
        //3 to id ubrań

        super.kupuj(rob, giełda);
        int ileUbrańKupić = Math.max(0, 100 - rob.getZasoby().ubrania.size());
        ArrayList<OfertaSpekulanta> ofertyU = giełda.getOfertySprzedażySpekulantów().get(3);
        for (int i = 0; i < ofertyU.size() && ileUbrańKupić > 0; i++) {
            OfertaSpekulanta oferta = ofertyU.get(i);
            if (Math.min(oferta.getIlość(), ileUbrańKupić) * oferta.getCenaZaSztukę() <=
                    rob.getZasoby().diamenty) { //jeśli mamy wystarczające fundusze
                if (oferta.getIlość() > ileUbrańKupić) {
                    oferta.setIlość(oferta.getIlość() - ileUbrańKupić);
                    ofertyU.set(i, oferta);
                    rob.getZasoby().diamenty -= ileUbrańKupić * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += ileUbrańKupić * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[3] += ileUbrańKupić * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[3] += ileUbrańKupić;
                    for (int j = 0; j < ileUbrańKupić; j++) {
                        rob.getZasoby().ubrania.add(oferta.getJakość() * oferta.getJakość());
                        /* ubrania w zasobach są reprezentowane przez swoją wytrzymałość,
                        zaś w ofertach przez swoją jakość */
                    }
                    ileUbrańKupić= 0;
                } else {
                    ileUbrańKupić -= oferta.getIlość();
                    for (int j = 0; j < oferta.getIlość(); j++) {
                        rob.getZasoby().ubrania.add(oferta.getJakość());
                    }
                    rob.getZasoby().diamenty -= oferta.getIlość() * oferta.getCenaZaSztukę();
                    oferta.getSpekulant().getZasoby().diamenty += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.obrótPieniężnyProduktuDziś[3] += oferta.getIlość() * oferta.getCenaZaSztukę();
                    giełda.ilośćProduktuDziś[3] += oferta.getIlość();
                    ofertyU.remove(i);
                    i--;
                }
            } else {
                ileUbrańKupić = 0; //nic już nie kupimy
            }
        }
        giełda.setOfertySprzedażySpekulantów(3, ofertyU);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "czyścioszek");
        return ret;
    }
}
