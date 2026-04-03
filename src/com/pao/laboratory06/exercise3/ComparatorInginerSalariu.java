package com.pao.laboratory06.exercise3;

import java.util.Comparator;

public class ComparatorInginerSalariu implements Comparator<Inginer> {
    // descrescator dupa salariu
    @Override
    public int compare(Inginer a, Inginer b) {
        return Double.compare(b.getSalariu(), a.getSalariu());
    }
}