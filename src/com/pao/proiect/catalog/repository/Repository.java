package com.pao.proiect.catalog.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfata generica CRUD — cerina barem Etapa II.
 *
 * @param <T>  tipul entitatii  (ex: StudentDb, ProfesorDb)
 * @param <ID> tipul cheii primare (Long)
 */
public interface Repository<T, ID> {
    void           save(T entity)       throws SQLException;
    Optional<T>    findById(ID id)      throws SQLException;
    List<T>        findAll()            throws SQLException;
    void           update(T entity)     throws SQLException;
    void           delete(ID id)        throws SQLException;
}
