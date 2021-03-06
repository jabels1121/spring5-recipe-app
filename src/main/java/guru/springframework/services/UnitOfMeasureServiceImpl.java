package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.converter = converter;
    }

    @Override
    public Iterable<UnitOfMeasure> findAll() {
        return unitOfMeasureRepository.findAll();
    }

    @Override
    public UnitOfMeasure findById(String id) throws NoSuchElementException {
        Optional<UnitOfMeasure> byId = unitOfMeasureRepository.findById(id);
        return byId.orElseThrow();
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUomCommands() {
        return StreamSupport.stream(findAll().spliterator(), false)
                .map(converter::convert).collect(Collectors.toSet());
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
