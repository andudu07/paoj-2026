package com.pao.proiect.catalog.model;

import java.util.Objects;

public abstract class Persoana {

    private static int contor = 0;

    private final int id;
    private String nume;
    private String prenume;
    private String email;

    public Persoana(String nume, String prenume, String email) {
        this.id = ++contor;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public abstract String getRol();

    public String getNumeComplet() { return prenume + " " + nume; }

    public int getId()         { return id; }
    public String getNume()    { return nume; }
    public String getPrenume() { return prenume; }
    public String getEmail()   { return email; }

    public void setNume(String nume)       { this.nume = nume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }
    public void setEmail(String email)     { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persoana p)) return false;
        return id == p.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("[%s] %s (id=%d)", getRol(), getNumeComplet(), id);
    }
}
