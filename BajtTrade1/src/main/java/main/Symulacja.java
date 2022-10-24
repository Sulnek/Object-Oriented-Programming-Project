package main;

import java.util.*;

public class Symulacja {
    private ArrayList<Robotnik> robotnicy;

    private ArrayList<Robotnik> martwiRobotnicy;

    private int długość;

    private int karaZaBrakUbrań;
    private Giełda giełda;
    private ArrayList<Spekulant> spekulanci;

    public Symulacja(int dł, int kara, Giełda giełda1) {
        długość = dł;
        karaZaBrakUbrań = kara;
        giełda = giełda1;
        robotnicy = new ArrayList<Robotnik>();
        spekulanci = new ArrayList<Spekulant>();
        martwiRobotnicy = new ArrayList<Robotnik>();
    }

    public Symulacja(ArrayList<Robotnik> rob, int dl, Giełda gie, ArrayList<Spekulant> spek) {
        robotnicy = rob;
        giełda = gie;
        spekulanci = spek;
        długość = dl;
    }

    public void rozbudźGłódRoboników() {
        for (int i = 0; i < robotnicy.size(); i++) {
            Robotnik rob = robotnicy.get(i);
            rob.setIleBezJedzenia(rob.getIleBezJedzenia() + 1);
            if (rob.getIleBezJedzenia() >= 4) {
                robotnicy.remove(i);
                rob.getZasoby().diamenty = 0;
                martwiRobotnicy.add(rob);
                i--;
            }
        }
    }

    public void nagichOdziej() {
        for (int i = 0; i < robotnicy.size(); i++) {
            Robotnik rob = robotnicy.get(i);
            rob.ubierajSię(karaZaBrakUbrań);
        }
    }

    public void głodnychNakarm() {
        for (int i = 0; i < robotnicy.size(); i++) {
            Robotnik rob = robotnicy.get(i);
            rob.jedz();
        }
    }
    public void angażujRobotników(int tura) {
        for (Robotnik rob : robotnicy) {
            if (rob.getUczenie().czySięUczę(rob, giełda.getHistoriaGiełdy(), tura)) {
                rob.setIleBezJedzenia(0);
                if (rob.czyZmieniamKarierę(tura)) {
                    rob.zmieńKarierę(giełda.getHistoriaGiełdy());
                }
                else {
                    rob.rozwijajKarierę();
                }
            }
            else {
                int idProduktu = rob.getProdukcja().coProdukuję(giełda.getHistoriaGiełdy(), rob);
                rob.produkuj(idProduktu);
            }
        }
    }

    public void zbierzOfertySpekulantów() {
        giełda.zerujOfertyKupna();
        giełda.zerujOfertySprzedaży();
        for (Spekulant i : spekulanci) {
            i.wystawOfertyKupna(giełda);
            i.wystawOfertySprzedaży(giełda);
        }
        AtrakcyjnośćOferty comp = new AtrakcyjnośćOferty();
        for (int i = 0; i < 4; i++) {
            giełda.getOfertySprzedażySpekulantów().get(i).sort(comp);
            giełda.getOfertyKupnaSpekulantów().get(i).sort(comp);
        }
    }

    public void robotnicyHandlują(int tura) {
        robotnicy.sort(new ZamożnośćRobotnikaNierosnąco());
        if (giełda.czyRuchKapitalistyczny(tura)) {
            for (int i = 0; i < robotnicy.size(); i++) {
                robotnicy.get(i).sprzedawaj(giełda);
                robotnicy.get(i).kupuj(giełda);
            }
        } else {
            for (int i = robotnicy.size() - 1; i >= 0; i--) {
                robotnicy.get(i).sprzedawaj(giełda);
                robotnicy.get(i).kupuj(giełda);
            }
        }
        for (Robotnik i : robotnicy) {
            i.zostańWykupiony();
        }
    }

    public void dopiszHistorię() {
        HistoriaGiełdy historia = giełda.getHistoriaGiełdy();
        double śr[] = new double[4];
        for (int i = 0; i < 4; i++) {
            if (giełda.ilośćProduktuDziś[i] > 0) {
                śr[i] = giełda.obrótPieniężnyProduktuDziś[i] / giełda.ilośćProduktuDziś[i];
            } else {
                śr[i] = historia.getŚredniaCena(i, 1);
            }
        }
        historia.zapiszKolejnyDzień(
                śr[0], giełda.ilośćProduktuDziś[0],
                śr[1], giełda.ilośćProduktuDziś[1],
                śr[2], giełda.ilośćProduktuDziś[2],
                śr[3], giełda.ilośćProduktuDziś[3]);
        for (int i = 0; i < 4; i++) {
            giełda.ilośćProduktuDziś[i] = 0;
            giełda.obrótPieniężnyProduktuDziś[i] = 0;
        }
    }

    public void zwróćOfertySpekulantów() {
        for (int i = 0; i < 4; i++) {
            ArrayList<OfertaSpekulanta> oferty = giełda.getOfertySprzedażySpekulantów().get(i);
            for (OfertaSpekulanta oferta : oferty) {
                if (i == 0) { //jedzenie
                    oferta.getSpekulant().getZasoby().jedzenie += oferta.getIlość();
                } else {
                    for (int j = 0; j < oferta.getIlość(); j++) {
                        oferta.getSpekulant().getZasobyJakościowe(i).add(oferta.getJakość());
                    }
                }
            }
        }
    }

    public Giełda getGiełda() {
        return giełda;
    }

    public ArrayList<Robotnik> getRobotnicy() {
        return robotnicy;
    }

    public ArrayList<Robotnik> getMartwiRobotnicy() {
        return martwiRobotnicy;
    }

    public ArrayList<Spekulant> getSpekulanci() {
        return spekulanci;
    }

    public void setRobotnicy(ArrayList<Robotnik> rob) {
        robotnicy = rob;
    }

    public void setSpekulanci(ArrayList<Spekulant> spek) {
        spekulanci = spek;
    }

    public void symulacjaMain() {
        for (int i = 1; i <= długość; i++) {
            rozbudźGłódRoboników();
            nagichOdziej();
            angażujRobotników(i);
            głodnychNakarm();
            zbierzOfertySpekulantów(); // kupna i sprzedaży
            robotnicyHandlują(i);
            zwróćOfertySpekulantów();
            dopiszHistorię();
            JsonParser.wypiszDzień(this, i);
        }
    }
}
