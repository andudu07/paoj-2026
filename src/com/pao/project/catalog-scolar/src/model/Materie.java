package com.pao.proiect.catalog.model;

import java.util.Objects;

public class Materie {

    private final CodMaterie cod;
    private String denumire;
    private int credite;
    private Profesor profesor;

    public Materie(CodMaterie cod, String denumire, int credite, Profesor profesor) {
        this.cod = cod;
        this.denumire = denumire;
        this.credite  = credite;
        this.profesor = profesor;
    }

    public CodMaterie getCod()    { return cod; }
    public String getDenumire()   { return denumire; }
    public int getCredite()       { return credite; }
    public Profesor getProfesor() { return profesor; }

    public void setDenumire(String denumire)  { this.denumire = denumire; }
    public void setCredite(int credite)       { this.credite = credite; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Materie m)) return false;
        return Objects.equals(cod, m.cod);
    }

    @Override
    public int hashCode() { return Objects.hash(cod); }

    @Override
    public String toString() {
        return String.format("[Materie] %s - %s | %d credite | Titular: %s",
                cod, denumire, credite, profesor.getNumeComplet());
    }
}
