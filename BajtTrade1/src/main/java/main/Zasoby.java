package main;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Zasoby {
    public int jedzenie;
    public ArrayList<Integer> programy;
    public ArrayList<Integer> narzędzia;
    public ArrayList<Integer> ubrania;
    public int diamenty;

    public Zasoby() {}

    public Zasoby(int jedz, int pro, int narz, int ubr, int dia) {
        jedzenie = jedz;
        diamenty = dia;
        programy = new ArrayList<Integer>();
        narzędzia = new ArrayList<Integer>();
        ubrania = new ArrayList<Integer>();
        for (int i = 0; i < pro; i++) {
            programy.add(1);
        }
        for (int i = 0; i < narz; i++) {
            narzędzia.add(1);
        }
        for (int i = 0; i < ubr; i++) {
            ubrania.add(1 * 1);
        }
    }

    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("jedzenie", jedzenie);
        ret.put("diamenty", diamenty);

        JSONArray programyJ = new JSONArray();
        for (Integer i : programy) {
            programyJ.add(i);
        }
        ret.put("programy", programyJ);

        JSONArray narzędziaJ = new JSONArray();
        for (Integer i : narzędzia) {
            narzędziaJ.add(i);
        }
        ret.put("narzędzia", narzędziaJ);

        JSONArray ubraniaJ = new JSONArray();
        for (Integer i : ubrania) {
            ubraniaJ.add(i);
        }
        ret.put("ubrania", ubraniaJ);
        return ret;
    }
}
