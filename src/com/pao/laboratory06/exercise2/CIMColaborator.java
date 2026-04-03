package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitBrutLunar = in.nextDouble();
        if (in.hasNext("[DA|NU]") || in.hasNext("DA") || in.hasNext("NU")) {
            bonus = in.next().equals("DA");
        } else {
            bonus = false;
        }
    }

    @Override
    public boolean areBonus() { return bonus; }

    @Override
    public double calculeazaVenitNetAnual() {
        double net = venitBrutLunar * 12 * 0.55;
        if (bonus) net *= 1.10;
        return net;
    }

    @Override
    public String tipContract() { return "CIM"; }

    @Override
    public TipColaborator getTip() { return TipColaborator.CIM; }
}