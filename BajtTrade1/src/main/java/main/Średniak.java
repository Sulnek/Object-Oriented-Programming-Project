package main;

public class Średniak extends TypProdukcji {
    private int historiaŚredniejProdukcji;

    @Override
    public int coProdukuję(HistoriaGiełdy historia, Robotnik rob) {
        return historia.najwyższaŚrednia(historiaŚredniejProdukcji);
    }

    public Średniak(int his) {
        historiaŚredniejProdukcji = his;
    }

    @Override
    public org.json.simple.JSONObject toJSONObject() {
        org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
        ret.put("typ", "średniak");
        ret.put("historia_średniej_produkcji", historiaŚredniejProdukcji);
        return ret;
    }
}