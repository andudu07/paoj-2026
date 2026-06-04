package com.pao.proiect.catalog.model.db;

/**
 * Entitate persistabila - mapeaza tabela `profesor`
 * Separata de Profesor pentru a nu amesteca
 * logica de business cu persistenta
 */
public class ProfesorDb {
    private long   id;
    private String nume;
    private String prenume;
    private String email;
    private String titlu;
    private String departament;

    public ProfesorDb() {}

    public ProfesorDb(String nume, String prenume, String email,
                      String titlu, String departament) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.titlu = titlu;
        this.departament = departament;
    }

    public long   getId()           { return id; }
    public void   setId(long id)    { this.id = id; }
    public String getNume()         { return nume; }
    public void   setNume(String n) { this.nume = n; }
    public String getPrenume()         { return prenume; }
    public void   setPrenume(String p) { this.prenume = p; }
    public String getEmail()           { return email; }
    public void   setEmail(String e)   { this.email = e; }
    public String getTitlu()           { return titlu; }
    public void   setTitlu(String t)   { this.titlu = t; }
    public String getDepartament()          { return departament; }
    public void   setDepartament(String d)  { this.departament = d; }

    @Override
    public String toString() {
        return String.format("ProfesorDb{id=%d, %s %s %s, dept=%s}",
                id, titlu, prenume, nume, departament);
    }
}
