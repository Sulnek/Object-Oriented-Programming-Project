package main;

public class Rozkładowy extends TypNauki {
    @Override
    public boolean czySięUczę(Robotnik robotnik, HistoriaGiełdy historiaGiełdy, int tura) {
        double random = Math.random();
        double chance = 1 / ((double) tura + 3);
        return chance < random;
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "rozkładowy");
        return ret;
    }
}
