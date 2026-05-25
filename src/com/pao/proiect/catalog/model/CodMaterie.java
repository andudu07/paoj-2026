package com.pao.proiect.catalog.model;

import java.util.Objects;

//clasa imuabila - identificator unic al unei materii
public final class CodMaterie {

    private final String prefix;
    private final int numar;
    private final String cod;

    public CodMaterie(String prefix, int numar) {
        this.prefix = prefix.toUpperCase().trim();
        this.numar  = numar;
        this.cod    = this.prefix + numar;
    }

    public String getPrefix() { return prefix; }
    public int getNumar()     { return numar; }
    public String getCod()    { return cod; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodMaterie c)) return false;
        return Objects.equals(cod, c.cod);
    }

    @Override
    public int hashCode() { return Objects.hash(cod); }

    @Override
    public String toString() { return cod; }
}
