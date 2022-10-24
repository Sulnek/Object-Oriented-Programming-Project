package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Main {
    public static void main(String[] Args) {
        JSONParser jsonParser = new JSONParser();

        try (Scanner sc = new Scanner(System.in)) {
            String plik = "";
            while(sc.hasNextLine()) {
                plik += sc.nextLine();
            }
            Object obj = jsonParser.parse(plik);

            JSONObject wejście = (JSONObject) obj;
            Symulacja sym = JsonParser.parseInfoObject(wejście);
            ArrayList<Robotnik> rob = JsonParser.parseRobotnicyObject(wejście);
            ArrayList<Spekulant> spek = JsonParser.parseSpekulanciObject(wejście);

            sym.setRobotnicy(rob);
            sym.setSpekulanci(spek);
            sym.symulacjaMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
