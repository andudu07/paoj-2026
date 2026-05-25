package com.pao.proiect.catalog.exception;

public class MaterieNedisponibilaException extends Exception {
    public MaterieNedisponibilaException(String cod) { super("Materia '" + cod + "' nu exista in sistem."); }
}
