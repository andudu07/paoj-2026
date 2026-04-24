package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.exception.MaterieNedisponibilaException;
import com.pao.proiect.catalog.model.Materie;
import com.pao.proiect.catalog.model.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class MaterieService {

    private static MaterieService instance;
    private MaterieService() {}
    public static MaterieService getInstance() {
        if (instance == null) instance = new MaterieService();
        return instance;
    }

    //map<cod, Materie> -indexare dupa cod unic
    private final Map<String, Materie> materii = new LinkedHashMap<>();

    public Materie adauga(Materie m) {
        materii.put(m.getCod().getCod(), m);
        System.out.println("Materie adaugata: " + m);
        return m;
    }

    public void elimina(String cod) throws MaterieNedisponibilaException {
        if (!materii.containsKey(cod)) throw new MaterieNedisponibilaException(cod);
        System.out.println("Materie eliminata: " + materii.remove(cod).getDenumire());
    }

    public Materie cautaDupaCod(String cod) throws MaterieNedisponibilaException {
        Materie m = materii.get(cod);
        if (m == null) throw new MaterieNedisponibilaException(cod);
        return m;
    }

    public List<Materie> listeazaToti() { return new ArrayList<>(materii.values()); }

    // grupare Map<Profesor, List<Materie>>
    public Map<Profesor, List<Materie>> grupeazaDupaProfesor() {
        return materii.values().stream().collect(Collectors.groupingBy(Materie::getProfesor));
    }

    public List<Materie> materiiProfesor(int profesorId) {
        return materii.values().stream()
                .filter(m -> m.getProfesor().getId() == profesorId)
                .collect(Collectors.toList());
    }
}
