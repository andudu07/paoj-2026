package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.exception.MaterieNedisponibilaException;
import com.pao.proiect.catalog.exception.NotaInvalidaException;
import com.pao.proiect.catalog.exception.StudentNegasitException;
import com.pao.proiect.catalog.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class CatalogService {

    private static CatalogService instance;
    private CatalogService() {}
    public static CatalogService getInstance() {
        if (instance == null) instance = new CatalogService();
        return instance;
    }

    //Map<codMaterie, Map<studentId, Inregistrare>>
    private final Map<String, Map<Integer, Inregistrare>> catalog = new LinkedHashMap<>();

    public Inregistrare inscrie(Student student, Materie materie)
            throws StudentNegasitException, MaterieNedisponibilaException {
        if (student == null) throw new StudentNegasitException("Student null.");
        if (materie == null) throw new MaterieNedisponibilaException("null");

        String cod = materie.getCod().getCod();
        catalog.putIfAbsent(cod, new LinkedHashMap<>());
        Map<Integer, Inregistrare> map = catalog.get(cod);

        if (map.containsKey(student.getId())) {
            System.out.println("INFO: " + student.getNumeComplet() + " deja inscris la " + materie.getDenumire());
            return map.get(student.getId());
        }

        Inregistrare inreg = new Inregistrare(student, materie);
        map.put(student.getId(), inreg);
        System.out.printf("Inscris: %s -> %s%n", student.getNumeComplet(), materie.getDenumire());
        return inreg;
    }

    public Nota adaugaNota(Student student, Materie materie, double valoare)
            throws NotaInvalidaException, StudentNegasitException, MaterieNedisponibilaException {
        if (student == null) throw new StudentNegasitException("Student null.");
        if (materie == null) throw new MaterieNedisponibilaException("null");

        String cod = materie.getCod().getCod();
        Map<Integer, Inregistrare> map = catalog.get(cod);

        if (map == null || !map.containsKey(student.getId()))
            throw new StudentNegasitException(student.getNumeComplet() + " nu este inscris la " + materie.getDenumire());

        Nota nota = new Nota(valoare, student, materie);
        map.get(student.getId()).adaugaNota(nota);
        System.out.printf("Nota %.2f -> %s la %s%n", valoare, student.getNumeComplet(), materie.getDenumire());
        return nota;
    }

    public OptionalDouble mediaLaMaterie(Student student, Materie materie)
            throws StudentNegasitException, MaterieNedisponibilaException {
        if (student == null) throw new StudentNegasitException("Student null.");
        if (materie == null) throw new MaterieNedisponibilaException("null");

        Map<Integer, Inregistrare> map = catalog.get(materie.getCod().getCod());
        if (map == null || !map.containsKey(student.getId()))
            throw new StudentNegasitException(student.getNumeComplet() + " nu este inscris.");
        return map.get(student.getId()).getMedia();
    }

    public OptionalDouble mediaGenerala(Student student) {
        if (student == null) return OptionalDouble.empty();
        return catalog.values().stream()
                .filter(m -> m.containsKey(student.getId()))
                .map(m -> m.get(student.getId()))
                .flatMap(i -> i.getNote().stream())
                .mapToDouble(Nota::getValoare)
                .average();
    }

    public void afiseazaCatalog(Materie materie) throws MaterieNedisponibilaException {
        if (materie == null) throw new MaterieNedisponibilaException("null");
        String cod = materie.getCod().getCod();
        Map<Integer, Inregistrare> map = catalog.getOrDefault(cod, Collections.emptyMap());

        System.out.println("\n=== Catalog: " + materie.getDenumire() + " [" + cod + "] ===");
        System.out.println("Titular: " + materie.getProfesor().getNumeCuTitlu());
        System.out.println("---");
        if (map.isEmpty()) {
            System.out.println("(niciun student inscris)");
        } else {
            map.values().stream()
                    .sorted(Comparator.comparing(i -> i.getStudent().getNumeComplet()))
                    .forEach(System.out::println);
        }
        System.out.println("===\n");
    }

    public List<Student> studentiPromovati(Materie materie) throws MaterieNedisponibilaException {
        if (materie == null) throw new MaterieNedisponibilaException("null");
        return catalog.getOrDefault(materie.getCod().getCod(), Collections.emptyMap()).values().stream()
                .filter(Inregistrare::estePromovat)
                .map(Inregistrare::getStudent)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Student> studentiNepromovati(Materie materie) throws MaterieNedisponibilaException {
        if (materie == null) throw new MaterieNedisponibilaException("null");
        return catalog.getOrDefault(materie.getCod().getCod(), Collections.emptyMap()).values().stream()
                .filter(i -> !i.estePromovat())
                .map(Inregistrare::getStudent)
                .sorted()
                .collect(Collectors.toList());
    }

    public void eliminaStudent(Student student) {
        if (student == null) return;
        catalog.values().forEach(map -> map.remove(student.getId()));
        System.out.println("Inregistrarile lui " + student.getNumeComplet() + " au fost sterse.");
    }
}
