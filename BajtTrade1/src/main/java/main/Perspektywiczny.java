package main;

import org.json.simple.JSONObject;

public class Perspektywiczny extends TypProdukcji{
    private int historiaPerspektywy;
    @Override
    public int coProdukuję(HistoriaGiełdy historia, Robotnik rob) {
        double średniaCena = 0;
        int idProduktu = 0;
        for (int i = 1; i <= 3; i++) {
            if (średniaCena < historia.średniaCena(i, 1) - historia.średniaCena(i, historiaPerspektywy)) {
                idProduktu = i;
                średniaCena = historia.średniaCena(i, 1) - historia.średniaCena(i, historiaPerspektywy);
            }
        }
        if (średniaCena < historia.średniaCena(0, 1) - historia.średniaCena(0, historiaPerspektywy)) {
            idProduktu = 0;
            średniaCena = historia.średniaCena(0, 1) - historia.średniaCena(0, historiaPerspektywy);
        }
        return idProduktu;
    }

    public Perspektywiczny(int his) {
        historiaPerspektywy = his;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "perspektywiczny");
        ret.put("historia_perspektywy", historiaPerspektywy);
        return ret;
    }
}
