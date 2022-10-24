package main;

import java.util.*;

public class HistoriaGiełdy {
    private ArrayList<ArrayList<Double>> średniaCena;
    private ArrayList<ArrayList<Double>> ileSrzedano;

    public double średniaCenaJedzenia(int okres, int tura) {
        double suma = 0;
        for (int i = tura - 1; i >= tura - okres && i >= 0; i--) {
            suma += średniaCena.get(0).get(i);
        }
        return suma / Math.min(okres, tura - 1);
    }

    public HistoriaGiełdy(Ceny ceny) {
        średniaCena = new ArrayList<ArrayList<Double>>();
        ileSrzedano = new ArrayList<ArrayList<Double>>();
        for (int i = 0; i < 4; i++) {
            średniaCena.add(new ArrayList<Double>());
            ileSrzedano.add(new ArrayList<Double>());
        }
        zapiszKolejnyDzień(ceny.jedzenie, 1, ceny.programy, 1,
                           ceny.narzędzia, 1, ceny.ubrania, 1);
    }

    public void zapiszKolejnyDzień(double śrJedzenia, double ilJedzenia,
                              double śrProgramów, double ilProgramów,
                              double śrNarzędzi, double ilNarzędzi,
                              double śrUbrań, double ilUbrań) {
        średniaCena.get(0).add(śrJedzenia);
        średniaCena.get(1).add(śrProgramów);
        średniaCena.get(2).add(śrNarzędzi);
        średniaCena.get(3).add(śrUbrań);

        ileSrzedano.get(0).add(ilJedzenia);
        ileSrzedano.get(1).add(ilProgramów);
        ileSrzedano.get(2).add(ilNarzędzi);
        ileSrzedano.get(3).add(ilUbrań);
    }
    
    public int najczęstszyProdukt(int n, int kariera) {
        double[] sum = new double[4];
        int length = ileSrzedano.get(0).size();
        for (int i = 0; i < Math.min(n, length); i++) {
            for(int j = 0; j < 4; j++) {
                sum[j] += ileSrzedano.get(j).get(length - 1 - i);
            }
        }

        if (sum[1] >= Math.max(sum[0], Math.max(sum[2], sum[3]))) {
            return 1; //programy
        } else if (sum[2] >= Math.max(sum[0], sum[3])) {
            return 2; // narzędzia
        } else if (sum[3] >= sum[0]) {
            return 3; //ubrania
        } else {
            return 0; //jedzenie
        } //takie są priorytety zmiany kariery
    }

    /**
     * Zwraca średnią cenę produktu o id "idP" na przestrzeni
     * ostatniczh "termin" dni.
     * @param idP
     * @param termin
     * @return
     */
    public double średniaCena(int idP, double termin) {
        int length = średniaCena.get(0).size();
        double suma = 0;
        for (int i = 0; i < Math.min(length, termin); i++) {
            suma += średniaCena.get(idP).get(length - i - 1);
        }
        return suma / Math.min(length, termin);
    }

    /**
     * Zwraca średnią cenę produktu o id "idP", "kiedy" dni temu
     * @param idP
     * @param kiedy
     * @return
     */
    public double getŚredniaCena(int idP, int kiedy) {
        int length = średniaCena.get(0).size();
        return średniaCena.get(idP).get(length - kiedy);
    }

    /**
     * zwraca id produktu o najwyższej średniej na przestarzeni ostatnich kiedy dni
     * @param kiedy
     * @return id
     */
    public int najwyższaŚrednia(int kiedy) {
        int length = średniaCena.get(0).size();
        int maksId = 1;
        double maksŚr = 0;
        double suma;
        double średnia;
        for (int idP = 1; idP < 4; idP++) {
            suma = 0;
            for (int i = 0; i <  Math.min(kiedy, length); i++) {
                suma += średniaCena.get(idP).get(length - i - 1) * ileSrzedano.get(idP).get(length - i - 1);
            }
            średnia = suma / (double) Math.min(kiedy, length);
            if (średnia > maksŚr) {
                maksŚr = średnia;
                maksId = idP;
            }
        }
        suma = 0;
        for (int i = 0; i <  Math.min(kiedy, length); i++) {
            suma += średniaCena.get(0).get(length - i - 1) * ileSrzedano.get(0).get(length - i - 1);
        }
        średnia = suma / (double) Math.min(kiedy, length);
        if (średnia > maksŚr) {
            maksŚr = średnia;
            maksId = 0;
        }
        return maksId;
    }

    public double maksymalnaLiczbaProduktu(int id) {
        double ret = 0;
        for (int i = 0; i < ileSrzedano.get(id).size() - 1; i++) {
            ret = Math.max(ret, ileSrzedano.get(id).get(i));
        }
        return ret;
    }

    public double getIleSprzedano(int id, int kiedy) {
        int length = ileSrzedano.get(id).size();
        return ileSrzedano.get(id).get(length - kiedy);
    }

    public int getTura() {
        return średniaCena.get(0).size();
    }
}
