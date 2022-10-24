package main;

public class Oszczędny extends TypNauki {
    private double limit_diamentów;
    @Override
    public boolean czySięUczę(Robotnik robotnik, HistoriaGiełdy historiaGiełdy, int tura) {
        return robotnik.getZasoby().diamenty > limit_diamentów;
    }

    public Oszczędny(double limit_diamentów) {
        this.limit_diamentów = limit_diamentów;
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "oszczędny");
        ret.put("limit_diamentów", limit_diamentów);
        return ret;
    }
}
