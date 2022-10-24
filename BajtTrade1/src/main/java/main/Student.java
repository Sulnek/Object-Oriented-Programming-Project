package main;

public class Student extends TypNauki {
    private double zapas;
    private int okres;

    @Override
    public boolean czySięUczę(Robotnik robotnik, HistoriaGiełdy historiaGiełdy, int tura) {
        double średnia = historiaGiełdy.średniaCenaJedzenia(okres, tura);
        return średnia * zapas * 100 <= robotnik.getZasoby().diamenty;
    }

    public Student(int okres, double zapas) {
        this.okres = okres;
        this.zapas = zapas;
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "student");
        ret.put("zapas", zapas);
        ret.put("okres", okres);
        return ret;
    }
}
