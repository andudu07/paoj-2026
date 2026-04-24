package com.pao.proiect.catalog.exception;

public class NotaInvalidaException extends Exception {
    public NotaInvalidaException(double val) {
        super(String.format("Nota %.2f este invalida. Valorile acceptate sunt intre 1 si 10.", val));
    }
}
