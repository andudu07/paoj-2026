package com.pao.proiect.catalog.model.db;

/**
 * Entitate persistabila - mapeaza tabela `student`
 */
public class StudentDb {
    private long   id;
    private String nume;
    private String prenume;
    private String email;
    private String grupa;
    private int    anStudiu;

    public StudentDb() {}

    public StudentDb(String nume, String prenume, String email,
                     String grupa, int anStudiu) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.grupa = grupa;
        this.anStudiu = anStudiu;
    }

    public long   getId()              { return id; }
    public void   setId(long id)       { this.id = id; }
    public String getNume()            { return nume; }
    public void   setNume(String n)    { this.nume = n; }
    public String getPrenume()         { return prenume; }
    public void   setPrenume(String p) { this.prenume = p; }
    public String getEmail()           { return email; }
    public void   setEmail(String e)   { this.email = e; }
    public String getGrupa()           { return grupa; }
    public void   setGrupa(String g)   { this.grupa = g; }
    public int    getAnStudiu()        { return anStudiu; }
    public void   setAnStudiu(int a)   { this.anStudiu = a; }

    @Override
    public String toString() {
        return String.format("StudentDb{id=%d, %s %s, grupa=%s, an=%d}",
                id, prenume, nume, grupa, anStudiu);
    }
}
