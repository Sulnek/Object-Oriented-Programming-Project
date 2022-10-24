package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {
    public static Ceny parseCenyObject(JSONObject cenyObject) {
        double programy = ((Number) cenyObject.get("programy")).doubleValue();
        double jedzenie = ((Number) cenyObject.get("jedzenie")).doubleValue();
        double ubrania = ((Number) cenyObject.get("ubrania")).doubleValue();
        double narzędzia = ((Number) cenyObject.get("narzedzia")).doubleValue();
        return new Ceny(programy, jedzenie, ubrania, narzędzia);
    }

    public static Symulacja parseInfoObject(JSONObject info) {
        JSONObject infoObject = (JSONObject) info.get("info");
        long  długość = (Long) infoObject.get("dlugosc");
        String typGiełdyStr = (String) infoObject.get("gielda");
        int gielda;
        if (typGiełdyStr.charAt(0) == 'K') {
            gielda = 0;
        } else if (typGiełdyStr.charAt(0) == 'Z') {
            gielda = 1;
        } else {
            gielda = 2;
        }
        long kara = (Long) infoObject.get("kara_za_brak_ubrań");
        Ceny ceny = parseCenyObject((JSONObject) infoObject.get("ceny"));
        Giełda g = new Giełda(gielda, ceny);
        return new Symulacja((int) długość, (int) kara, g);
    }

    public static ArrayList<Robotnik> parseRobotnicyObject(JSONObject robotnicy) {
        ArrayList<Robotnik> ret = new ArrayList<Robotnik>();
        JSONArray robotnicyListObject = (JSONArray) robotnicy.get("robotnicy");
        for (Object j : robotnicyListObject) {
            JSONObject i = (JSONObject) j;
            int id = ((Long) i.get("id")).intValue();
            int poziom = ((Long) i.get("poziom")).intValue();

            String karieraStr = (String) i.get("kariera");
            int kariera;
            if (karieraStr.charAt(0) == 'r' && karieraStr.charAt(1) == 'o') {
                kariera = 0;
            } else if (karieraStr.charAt(0) == 'p') {
                kariera = 1;
            } else if (karieraStr.charAt(0) == 'i') {
                kariera = 2;
            } else if (karieraStr.charAt(0) == 'r' && karieraStr.charAt(1) == 'z') {
                kariera = 3;
            } else {
                kariera = 4; //górnik
            }

            Object produkcjaObject = i.get("produkcja");
            TypProdukcji produkcja = parseProdukcjaObject((JSONObject) produkcjaObject);

            Object uczenieObject = i.get("uczenie");
            TypNauki uczenie = parseUczenieObject((JSONObject) uczenieObject);

            int zmiana;
            String zmianaStr = (String) i.get("zmiana");
            if (zmianaStr.charAt(0) == 'k') {
                zmiana = 0;
            } else {
                zmiana = 1;
            }

            int[] produktywność = new int[5];
            JSONObject produktywnośćObject = (JSONObject) i.get("produktywnosc");
            produktywność[0] = ((Long) produktywnośćObject.get("jedzenie")).intValue();
            produktywność[1] = ((Long) produktywnośćObject.get("programy")).intValue();
            produktywność[2] = ((Long) produktywnośćObject.get("narzedzia")).intValue();
            produktywność[3] = ((Long) produktywnośćObject.get("ubrania")).intValue();
            produktywność[4] = ((Long) produktywnośćObject.get("diamenty")).intValue();

            JSONObject zasobyObject = (JSONObject) i.get("zasoby");
            int zasJ, zasP, zasN, zasU, zasD;
            zasJ = ((Long) zasobyObject.get("jedzenie")).intValue();
            zasP = ((Long) zasobyObject.get("programy")).intValue();
            zasN = ((Long) zasobyObject.get("narzedzia")).intValue();
            zasU = ((Long) zasobyObject.get("ubrania")).intValue();
            zasD = ((Long) zasobyObject.get("diamenty")).intValue();
            Zasoby zasoby = new Zasoby(zasJ, zasP, zasN, zasU, zasD);

            Object kupowanieObject = i.get("kupowanie");
            TypKupowania kupowanie;
            String kupowanieTyp = (String) ((JSONObject) kupowanieObject).get("typ");
            if (kupowanieTyp.charAt(0) == 'z') {
                kupowanie = new Zmechanizowany(((Long) ((JSONObject) kupowanieObject).get("liczba_narzedzi")).intValue());
            } else if(kupowanieTyp.charAt(0) == 'g') {
                kupowanie = new Gadżeciarz(((Long) ((JSONObject) kupowanieObject).get("liczba_narzedzi")).intValue());
            } else if (kupowanieTyp.charAt(0) == 'c') {
                kupowanie = new Czyścioszek();
            } else {
                kupowanie = new Technofob();
            }

            ret.add(new Robotnik(id, poziom, kariera, kupowanie, produkcja, uczenie, zasoby, produktywność));
        }
        return ret;
    }

    public static ArrayList<Spekulant> parseSpekulanciObject(JSONObject spekulanci) {
        ArrayList<Spekulant> ret = new ArrayList<>();
        JSONArray spekulanciListObject = (JSONArray) spekulanci.get("spekulanci");
        for (Object j : spekulanciListObject) {
            JSONObject i = (JSONObject) j;
            int id = ((Long) i.get("id")).intValue();

            int zasJ, zasP, zasN, zasU, zasD;
            JSONObject zasobyObject = (JSONObject) i.get("zasoby");
            zasJ = ((Long) zasobyObject.get("jedzenie")).intValue();
            zasP = ((Long) zasobyObject.get("programy")).intValue();
            zasN = ((Long) zasobyObject.get("narzedzia")).intValue();
            zasU = ((Long) zasobyObject.get("ubrania")).intValue();
            zasD = ((Long) zasobyObject.get("diamenty")).intValue();
            Zasoby zasoby = new Zasoby(zasJ, zasP, zasN, zasU, zasD);

            Object strategiaObject = (Object) i.get("kariera");
            String pom = new String();
            TypStrategii strategia;
            if (strategiaObject.getClass().equals(pom.getClass())) {
                String strategiaString = (String) strategiaObject;
                if (strategiaString.charAt(0) == 'W') {
                    strategia = new SpekulantWypukły();
                } else {
                    strategia = new SpekulantRegulującyRynek();
                }
            } else  {
                String strategiaString = (String) ((JSONObject) strategiaObject).get("typ");
                strategia = new SpekulantŚredni(((Long) ((JSONObject) strategiaObject).get("historia_spekulanta_sredniego")).intValue());
            }
            ret.add(new Spekulant(id, strategia, zasoby));
        }
        return ret;
    }

    public static TypProdukcji parseProdukcjaObject(JSONObject produkcja) {
        String typProdukcji = (String) produkcja.get("typ");
        if (typProdukcji.charAt(0) == 'p') {
            return new Perspektywiczny((int) produkcja.get("historia_perspektywy"));
        } else if (typProdukcji.charAt(0) == 'ś'){
            return new Średniak((int) produkcja.get("historia_średniej_produkcji"));
        } else if (typProdukcji.charAt(0) == 'k') {
            return new Krótkowzroczny();
        } else if (typProdukcji.charAt(0) == 'l') {
            return new Losowy();
        } else {
            return new Chciwy();
        }
    }

    public static TypNauki parseUczenieObject(JSONObject uczenie) {
        String typNauki = (String) uczenie.get("typ");
        if (typNauki.charAt(0) == 'o' && typNauki.charAt(1) == 's') {
            return new Oszczędny(((Long) uczenie.get("limit_diamentów")).intValue());
        } else if (typNauki.charAt(0) == 'o' && typNauki.charAt(1) == 'k'){
            return new Okresowy(((Long) uczenie.get("okresowosc_nauki")).intValue());
        } else if (typNauki.charAt(0) == 's') {
            return new Student(((Number) uczenie.get("okres")).intValue(),
                            ((Number) uczenie.get("zapas")).doubleValue());
        } else if (typNauki.charAt(0) == 'r') {
            return new Rozkładowy();
        } else {
            return new Pracuś();
        }
    }

    public static void wypiszDzień(Symulacja sym, int dzień) {
        JSONObject info = new JSONObject();
        info.put("dzień", dzień);

        JSONObject cenyŚrednie = new JSONObject();
        HistoriaGiełdy his = sym.getGiełda().getHistoriaGiełdy();
        cenyŚrednie.put("jedzenie", his.getŚredniaCena(0, his.getTura() - dzień + 1));
        cenyŚrednie.put("programy", his.getŚredniaCena(1, his.getTura() - dzień + 1));
        cenyŚrednie.put("narzędzia", his.getŚredniaCena(2, his.getTura() - dzień + 1));
        cenyŚrednie.put("ubrania", his.getŚredniaCena(3, his.getTura() - dzień + 1));
        info.put("ceny średnie", cenyŚrednie);

        JSONArray robotnicy = new JSONArray();
        for (Robotnik rob : sym.getRobotnicy()) {
            robotnicy.add(rob.toJSONObject());
        }
        for (Robotnik rob : sym.getMartwiRobotnicy()) {
            robotnicy.add(rob.toJSONObject());
        }

        JSONArray spekulanci = new JSONArray();
        for (Spekulant spek: sym.getSpekulanci()) {
            spekulanci.add(spek.toJSONObject());
        }

        JSONObject dzieńJSON = new JSONObject();
        dzieńJSON.put("info", info);
        dzieńJSON.put("robotnicy", robotnicy);
        dzieńJSON.put("spekulanci", spekulanci);
        System.out.println(dzieńJSON);
    }

}
