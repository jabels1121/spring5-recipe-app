package guru.springframework.services;

import guru.springframework.entities.UnitOfMeasure;

import java.util.List;
import java.util.NoSuchElementException;

public interface UnitOfMeasureService {
    List<UnitOfMeasure> findAll();

    UnitOfMeasure findById(Long id) throws NoSuchElementException;

    UnitOfMeasure save(UnitOfMeasure UnitOfMeasure);

    <T extends UnitOfMeasure> List<T> saveAll(Iterable<T> categories);

    void deleteById(Long id);

    void deleteAll();

    void delete(UnitOfMeasure UnitOfMeasure);
}
