package main;

public abstract class TypNauki {
    public boolean czySięUczę(Robotnik robotnik, HistoriaGiełdy historiaGiełdy, int tura) {
        return false;
    }

    public org.json.simple.JSONObject toJSONObject() {
        return new org.json.simple.JSONObject();
    }
}
