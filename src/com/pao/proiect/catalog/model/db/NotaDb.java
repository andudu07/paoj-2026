package com.pao.proiect.catalog.model.db;

/**
 * Entitate persistabila - mapeaza tabela `nota`
 */
public class NotaDb {
    private long   id;
    private long   studentId;
    private long   materieId;
    private double valoare;
    private String dataNota;   // ISO: YYYY-MM-DD

    public NotaDb() {}

    public NotaDb(long studentId, long materieId, double valoare, String dataNota) {
        this.studentId = studentId;
        this.materieId = materieId;
        this.valoare = valoare;
        this.dataNota = dataNota;
    }

    public long   getId()               { return id; }
    public void   setId(long id)        { this.id = id; }
    public long   getStudentId()        { return studentId; }
    public void   setStudentId(long s)  { this.studentId = s; }
    public long   getMaterieId()        { return materieId; }
    public void   setMaterieId(long m)  { this.materieId = m; }
    public double getValoare()          { return valoare; }
    public void   setValoare(double v)  { this.valoare = v; }
    public String getDataNota()         { return dataNota; }
    public void   setDataNota(String d) { this.dataNota = d; }

    @Override
    public String toString() {
        return String.format("NotaDb{id=%d, studentId=%d, materieId=%d, valoare=%.2f, data=%s}",
                id, studentId, materieId, valoare, dataNota);
    }
}
