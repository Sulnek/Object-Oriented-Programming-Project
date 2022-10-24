package main;

import org.json.simple.JSONObject;

import java.util.*;

public class Losowy extends TypProdukcji{
    @Override
    public int coProdukuję(HistoriaGiełdy historia ,Robotnik rob) {
        Random rand = new Random();
        return rand.nextInt(5);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "losowy");
        return ret;
    }
}
