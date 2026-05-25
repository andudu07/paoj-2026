package com.pao.proiect.catalog.model.db;

/**
 * Entitate persistabila — mapeaza tabela `materie`.
 */
public class MaterieDb {
    private long   id;
    private String cod;        // ex: INFO201
    private String denumire;
    private int    credite;
    private long   profesorId;

    public MaterieDb() {}

    public MaterieDb(String cod, String denumire, int credite, long profesorId) {
        this.cod = cod;
        this.denumire = denumire;
        this.credite = credite;
        this.profesorId = profesorId;
    }

    public long   getId()               { return id; }
    public void   setId(long id)        { this.id = id; }
    public String getCod()              { return cod; }
    public void   setCod(String c)      { this.cod = c; }
    public String getDenumire()         { return denumire; }
    public void   setDenumire(String d) { this.denumire = d; }
    public int    getCredite()          { return credite; }
    public void   setCredite(int c)     { this.credite = c; }
    public long   getProfesorId()       { return profesorId; }
    public void   setProfesorId(long p) { this.profesorId = p; }

    @Override
    public String toString() {
        return String.format("MaterieDb{id=%d, cod=%s, denumire='%s', credite=%d, profesorId=%d}",
                id, cod, denumire, credite, profesorId);
    }
}
