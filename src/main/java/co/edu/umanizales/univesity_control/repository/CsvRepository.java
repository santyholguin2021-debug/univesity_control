package co.edu.umanizales.univesity_control.repository;

import java.util.List;
import java.util.Optional;

public interface CsvRepository<T> {
    List<T> findAll();
    Optional<T> findById(String id);
    T save(T entity);
    void deleteById(String id);
    void saveAll(List<T> entities);
}
