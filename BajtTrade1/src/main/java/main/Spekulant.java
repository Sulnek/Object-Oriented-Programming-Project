package main;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Spekulant {
    private int id;
    private TypStrategii strategia;
    private Zasoby zasoby;

    public Spekulant(int id, TypStrategii strategia, Zasoby zasoby) {
        this.id = id;
        this.strategia = strategia;
        this.zasoby = zasoby;
    }

    public void wystawOfertyKupna(Giełda giełda) {
        strategia.kupuj(giełda, this);
    }

    public void wystawOfertySprzedaży(Giełda giełda) {
        strategia.sprzedaj(giełda, this);
    }

    public Zasoby getZasoby() {
        return zasoby;
    }

    public ArrayList<Integer> getZasobyJakościowe(int id) {
        if (id == 1) {
            return zasoby.programy;
        } else if (id == 2) {
            return zasoby.narzędzia;
        } else { //id == 3
            return zasoby.ubrania;
        }
    }

    public JSONObject toJSONObject() {
       JSONObject ret = new JSONObject();
       ret.put("id", id);
       ret.put("zasoby", zasoby.toJSONObject());
       ret.put("kariera", strategia.toJSONObject());
       return ret;
    }
}
