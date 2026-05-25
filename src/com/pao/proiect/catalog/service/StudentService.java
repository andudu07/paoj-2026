package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.exception.StudentNegasitException;
import com.pao.proiect.catalog.model.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    private static StudentService instance;
    private StudentService() {}
    public static StudentService getInstance() {
        if (instance == null) instance = new StudentService();
        return instance;
    }

    private final Map<Integer, Student> studenti = new LinkedHashMap<>();

    public Student adauga(Student s) {
        studenti.put(s.getId(), s);
        System.out.println("Student adaugat: " + s);
        return s;
    }

    public void elimina(int id) throws StudentNegasitException {
        if (!studenti.containsKey(id)) throw new StudentNegasitException(id);
        System.out.println("Student eliminat: " + studenti.remove(id).getNumeComplet());
    }

    public Student cautaDupaId(int id) throws StudentNegasitException {
        Student s = studenti.get(id);
        if (s == null) throw new StudentNegasitException(id);
        return s;
    }

    public List<Student> cautaDupaNume(String fragment) {
        String f = fragment.toLowerCase();
        return studenti.values().stream()
                .filter(s -> s.getNumeComplet().toLowerCase().contains(f))
                .collect(Collectors.toList());
    }

    public List<Student> listeazaToti() {
        List<Student> lista = new ArrayList<>(studenti.values());
        Collections.sort(lista);
        return lista;
    }

    public List<Student> listeazaDupaGrupa(String grupa) {
        return studenti.values().stream()
                .filter(s -> s.getGrupa().equalsIgnoreCase(grupa))
                .sorted()
                .collect(Collectors.toList());
    }

    public int getNr() { return studenti.size(); }
}
