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

public abstract class TypStrategii {
    public void kupuj(Giełda giełda, Spekulant spekulant){}
    public void sprzedaj(Giełda giełda, Spekulant spekulant){}

    public org.json.simple.JSONObject toJSONObject() {
        return new JSONObject();
    }
}
