package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {

    private Angajat[] angajati;

    private AngajatService() {
        this.angajati = new Angajat[0];
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a) {
        Angajat[] enlarged = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, enlarged, 0, angajati.length);
        enlarged[angajati.length] = a;
        angajati = enlarged;
        System.out.println("Adugat: " + a);
    }

    public void printAll() {
        System.out.println("Toti angajatii");
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }

    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        System.out.println("Angajati dupa salariu (descrescator)");
        for (Angajat a : copy) {
            System.out.println(a);
        }
    }

    public void findByDepartament(String numeDept) {
        System.out.println("Angajati in departamentul: " + numeDept);
        boolean found = false;
        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Niciun angajat in departamentul: " + numeDept);
        }
    }
}