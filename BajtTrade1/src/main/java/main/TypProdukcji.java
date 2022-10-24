package main;

public abstract class TypProdukcji {
    public int coProdukuję(HistoriaGiełdy historia, Robotnik rob) {
        return 0;
    }

    public org.json.simple.JSONObject toJSONObject() {
        return new org.json.simple.JSONObject();
    }
}
