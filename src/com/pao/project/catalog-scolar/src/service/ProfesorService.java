package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.model.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class ProfesorService {

    private static ProfesorService instance;
    private ProfesorService() {}
    public static ProfesorService getInstance() {
        if (instance == null) instance = new ProfesorService();
        return instance;
    }

    private final Map<Integer, Profesor> profesori = new LinkedHashMap<>();

    public Profesor adauga(Profesor p) {
        profesori.put(p.getId(), p);
        System.out.println("Profesor adaugat: " + p);
        return p;
    }

    public void elimina(int id) {
        Profesor p = profesori.remove(id);
        if (p != null) System.out.println("Profesor eliminat: " + p.getNumeComplet());
    }

    public Optional<Profesor> cautaDupaId(int id) { return Optional.ofNullable(profesori.get(id)); }

    public List<Profesor> listeazaToti() { return new ArrayList<>(profesori.values()); }

    public List<Profesor> materiiProfesor(int id) {
        return profesori.values().stream()
                .filter(p -> p.getId() == id)
                .collect(Collectors.toList());
    }
}
