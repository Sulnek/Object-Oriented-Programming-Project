package main;

import org.json.simple.JSONObject;

public class Krótkowzroczny extends TypProdukcji {

    @Override
    public int coProdukuję(HistoriaGiełdy historia, Robotnik rob) {
        return historia.najwyższaŚrednia(1);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "krótkowzroczny");
        return ret;
    }
}
