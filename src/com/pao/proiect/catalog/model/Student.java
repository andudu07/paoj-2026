package com.pao.proiect.catalog.model;

import java.util.Objects;

public class Student extends Persoana implements Comparable<Student> {

    private String grupa;
    private int anStudiu;

    public Student(String nume, String prenume, String email, String grupa, int anStudiu) {
        super(nume, prenume, email);
        this.grupa = grupa;
        this.anStudiu = anStudiu;
    }

    @Override
    public String getRol() { return "Student"; }

    public String getGrupa()  { return grupa; }
    public int getAnStudiu()  { return anStudiu; }
    public void setGrupa(String grupa)     { this.grupa = grupa; }
    public void setAnStudiu(int anStudiu)  { this.anStudiu = anStudiu; }

    @Override
    public int compareTo(Student alt) {
        return this.getNumeComplet().compareToIgnoreCase(alt.getNumeComplet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student s)) return false;
        return super.equals(o) && Objects.equals(grupa, s.grupa);
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), grupa); }

    @Override
    public String toString() {
        return String.format("[Student] %s | Grupa: %s | An: %d | id=%d",
                getNumeComplet(), grupa, anStudiu, getId());
    }
}
