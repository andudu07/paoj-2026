# Catalog Scolar - PAO Project

## 1.1 - Actiuni posibile in sistem

1. Adauga un student nou
2. Adauga un profesor nou
3. Adauga o materie noua
4. Inscrie un student la o materie
5. Adauga o nota unui student la o materie
6. Calculeaza media unui student
7. Listeaza studentii dintr-o grupa
8. Cauta un student dupa nume sau id
9. Afiseaza catalogul complet al unei materii
10. Elimina un student din sistem
11. Afiseaza studentii promovati / picati la o materie
12. Listeaza materiile predate de un profesor

## 1.2 - Tipuri de obiecte

- Persoana (abstracta)
- Student
- Profesor
- Materie
- Nota
- CodMaterie (imuabila)
- Grupa
- Inregistrare

## 2. Implementare Java

**2.1 OOP** — 8 clase in `model/`; atribute private cu getteri/setteri; `toString/equals/hashCode` suprascrise in `Persoana`, `Student`, `Materie`, `CodMaterie`; ierarhie `Persoana` (abstracta, `getRol()`) → `Student` + `Profesor`; clasa imuabila `CodMaterie`; exceptii custom `StudentNegasitException`, `MaterieNedisponibilaException`, `NotaInvalidaException`.

**2.2 Colectii** — `List` (`Inregistrare`), `Set` (`Grupa`), `Map` (servicii); `Student` implementeaza `Comparable` pentru sortare alfabetica; `Map<Profesor, List<Materie>>` pentru grupare in `MaterieService`.

**2.3 Servicii** — `StudentService`, `ProfesorService`, `MaterieService`, `CatalogService`, toate Singleton (constructor privat + `getInstance()`); fiecare expune `adauga`, `elimina`, `cautaDupaId`, `listeazaToti`; `Main` demonstreaza toate cele 10 actiuni.

## Structura pachete

```
com.pao.proiect.catalog/
├── model/
├── service/
├── exception/
└── Main.java
```

