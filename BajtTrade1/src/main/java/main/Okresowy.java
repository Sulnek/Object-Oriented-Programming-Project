package main;

import org.json.simple.JSONObject;

public class Okresowy extends TypNauki{
    private int okresowośćNauki;

    @Override
    public boolean czySięUczę(Robotnik robotnik, HistoriaGiełdy historiaGiełdy, int tura) {
        return (tura % okresowośćNauki == 0);
    }

    public Okresowy(int okres) {
        okresowośćNauki = okres;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "okresowy");
        ret.put("okresowość_nauki", okresowośćNauki);
        return ret;
    }
}
