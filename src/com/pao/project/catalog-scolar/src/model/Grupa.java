package com.pao.proiect.catalog.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Grupa {

    private final String cod;
    private final int anStudiu;
    private final Set<Student> studenti = new LinkedHashSet<>();

    public Grupa(String cod, int anStudiu) {
        this.cod = cod;
        this.anStudiu = anStudiu;
    }

    public boolean adaugaStudent(Student s) { return studenti.add(s); }
    public boolean eliminaStudent(Student s) { return studenti.remove(s); }

    public String getCod()               { return cod; }
    public int getAnStudiu()             { return anStudiu; }
    public Set<Student> getStudenti()    { return Collections.unmodifiableSet(studenti); }
    public int getNrStudenti()           { return studenti.size(); }

    @Override
    public String toString() {
        return String.format("[Grupa] %s | An %d | %d studenti", cod, anStudiu, studenti.size());
    }
}
