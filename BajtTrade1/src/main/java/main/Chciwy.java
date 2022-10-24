package main;

import org.json.simple.JSONObject;

public class Chciwy extends TypProdukcji {
    @Override
    public int coProdukuję(HistoriaGiełdy historia, Robotnik rob) {
        int idProduktu = 0;
        double maksZysk = 0;
        for (int i = 1; i < 4; i++) {
            if (rob.ileProdukuję(i) * historia.getŚredniaCena(i, 1) > maksZysk) {
                maksZysk = rob.ileProdukuję(i) * historia.getŚredniaCena(i, 1);
                idProduktu = i;
            }
        }
        if (rob.ileProdukuję(0) * historia.getŚredniaCena(0, 1) > maksZysk) {
            maksZysk = rob.ileProdukuję(0) * historia.getŚredniaCena(0, 1);
            idProduktu = 0;
        }
        return idProduktu;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject ret = new JSONObject();
        ret.put("typ", "chciwy");
        return ret;
    }
}
