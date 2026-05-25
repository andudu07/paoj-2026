package com.pao.proiect.catalog.model;

public class Profesor extends Persoana {

    private String titlu;
    private String departament;

    public Profesor(String nume, String prenume, String email, String titlu, String departament) {
        super(nume, prenume, email);
        this.titlu = titlu;
        this.departament = departament;
    }

    @Override
    public String getRol() { return "Profesor"; }

    public String getTitlu()        { return titlu; }
    public String getDepartament()  { return departament; }
    public void setTitlu(String titlu)            { this.titlu = titlu; }
    public void setDepartament(String departament) { this.departament = departament; }

    public String getNumeCuTitlu() { return titlu + " " + getNumeComplet(); }

    @Override
    public String toString() {
        return String.format("[Profesor] %s %s | Dept: %s | id=%d",
                titlu, getNumeComplet(), departament, getId());
    }
}
