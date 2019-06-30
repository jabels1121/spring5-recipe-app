package guru.springframework.services;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureService(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    public List<UnitOfMeasure> findAll() {
        return unitOfMeasureRepository.findAll();
    }

    public UnitOfMeasure findById(Long id) throws NoSuchElementException {
        Optional<UnitOfMeasure> byId = unitOfMeasureRepository.findById(id);
        return byId.orElseThrow();
    }

    public UnitOfMeasure save(UnitOfMeasure UnitOfMeasure) {
        return unitOfMeasureRepository.save(UnitOfMeasure);
    }

    public <T extends UnitOfMeasure> List<T> saveAll(Iterable<T> categories) {
        return unitOfMeasureRepository.saveAll(categories);
    }

    public void deleteById(Long id) {
        unitOfMeasureRepository.deleteById(id);
    }

    public void deleteAll() {
        unitOfMeasureRepository.deleteAll();
    }

    public void delete(UnitOfMeasure UnitOfMeasure) {
        unitOfMeasureRepository.delete(UnitOfMeasure);
    }
}
