package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
                                    UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.converter = converter;
    }

    @Override
    public Flux<UnitOfMeasure> findAll() {
        return unitOfMeasureReactiveRepository.findAll();
        //return unitOfMeasureRepository.findAll();
    }

    @Override
    public UnitOfMeasure findById(String id) throws NoSuchElementException {
        Optional<UnitOfMeasure> byId = unitOfMeasureRepository.findById(id);
        return byId.orElseThrow();
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUomCommands() {
        return this.findAll().map(converter::convert);
    }

    @Override
    public UnitOfMeasure save(UnitOfMeasure UnitOfMeasure) {
        return unitOfMeasureRepository.save(UnitOfMeasure);
    }

    @Override
    public <T extends UnitOfMeasure> Iterable<T> saveAll(Iterable<T> categories) {
        return unitOfMeasureRepository.saveAll(categories);
    }

    @Override
    public void deleteById(String id) {
        unitOfMeasureRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        unitOfMeasureRepository.deleteAll();
    }

    @Override
    public void delete(UnitOfMeasure UnitOfMeasure) {
        unitOfMeasureRepository.delete(UnitOfMeasure);
    }
}
