package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.entities.UnitOfMeasure;

import java.util.NoSuchElementException;
import java.util.Set;

public interface UnitOfMeasureService {

    Iterable<UnitOfMeasure> findAll();

    UnitOfMeasure findById(String id) throws NoSuchElementException;

    UnitOfMeasure save(UnitOfMeasure UnitOfMeasure);

    Set<UnitOfMeasureCommand> listAllUomCommands();

    <T extends UnitOfMeasure> Iterable<T> saveAll(Iterable<T> categories);

    void deleteById(String id);

    void deleteAll();

    void delete(UnitOfMeasure UnitOfMeasure);
}
