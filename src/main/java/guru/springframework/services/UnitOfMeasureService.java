package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.entities.UnitOfMeasure;
import reactor.core.publisher.Flux;

import java.util.NoSuchElementException;
import java.util.Set;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasure> findAll();

    UnitOfMeasure findById(String id) throws NoSuchElementException;

    UnitOfMeasure save(UnitOfMeasure UnitOfMeasure);

    Flux<UnitOfMeasureCommand> listAllUomCommands();

    <T extends UnitOfMeasure> Iterable<T> saveAll(Iterable<T> categories);

    void deleteById(String id);

    void deleteAll();

    void delete(UnitOfMeasure UnitOfMeasure);
}
