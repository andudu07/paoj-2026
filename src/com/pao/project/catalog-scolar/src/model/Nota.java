package com.pao.proiect.catalog.model;

import com.pao.proiect.catalog.exception.NotaInvalidaException;

import java.time.LocalDate;

public class Nota {

    private final double valoare;
    private final LocalDate data;
    private final Student student;
    private final Materie materie;

    public Nota(double valoare, Student student, Materie materie) throws NotaInvalidaException {
        if (valoare < 1 || valoare > 10) throw new NotaInvalidaException(valoare);
        this.valoare  = valoare;
        this.data     = LocalDate.now();
        this.student  = student;
        this.materie  = materie;
    }

    public double getValoare()  { return valoare; }
    public LocalDate getData()  { return data; }
    public Student getStudent() { return student; }
    public Materie getMaterie() { return materie; }

    public boolean estePromovat() { return valoare >= 5.0; }

    @Override
    public String toString() {
        return String.format("Nota %.2f - %s la %s (%s)",
                valoare, student.getNumeComplet(), materie.getDenumire(), data);
    }
}
