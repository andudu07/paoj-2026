package com.pao.proiect.catalog.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

public class Inregistrare {

    private final Student student;
    private final Materie materie;
    private final List<Nota> note = new ArrayList<>();

    public Inregistrare(Student student, Materie materie) {
        this.student = student;
        this.materie = materie;
    }

    public void adaugaNota(Nota nota) { note.add(nota); }

    public OptionalDouble getMedia() {
        return note.stream().mapToDouble(Nota::getValoare).average();
    }

    public boolean estePromovat() {
        OptionalDouble media = getMedia();
        return media.isPresent() && media.getAsDouble() >= 5.0;
    }

    public Student getStudent()      { return student; }
    public Materie getMaterie()      { return materie; }
    public List<Nota> getNote()      { return Collections.unmodifiableList(note); }

    @Override
    public String toString() {
        String medieStr = getMedia().isPresent()
                ? String.format("%.2f", getMedia().getAsDouble()) : "fara note";
        return String.format("  %-30s | %d note | Medie: %s | %s",
                student.getNumeComplet(), note.size(), medieStr,
                estePromovat() ? "PROMOVAT" : "NEPROMOVAT");
    }
}
