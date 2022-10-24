package main;

public class Pracuś extends TypNauki {
    @Override
    public boolean czySięUczę(Robotnik robotnik, HistoriaGiełdy historiaGiełdy, int tura) {
        return false;
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "pracuś");
        return ret;
    }
}
