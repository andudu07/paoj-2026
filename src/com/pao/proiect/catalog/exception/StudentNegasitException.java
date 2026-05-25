package com.pao.proiect.catalog.exception;

public class StudentNegasitException extends Exception {
    public StudentNegasitException(int id) { super("Studentul cu id=" + id + " nu a fost gasit."); }
    public StudentNegasitException(String msg) { super(msg); }
}
