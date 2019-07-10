package guru.springframework.services;

import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public List<UnitOfMeasure> findAll() {
        return unitOfMeasureRepository.findAll();
    }

    @Override
    public UnitOfMeasure findById(Long id) throws NoSuchElementException {
        Optional<UnitOfMeasure> byId = unitOfMeasureRepository.findById(id);
        return byId.orElseThrow();
    }

    @Override
    public UnitOfMeasure save(UnitOfMeasure UnitOfMeasure) {
        return unitOfMeasureRepository.save(UnitOfMeasure);
    }

    @Override
    public <T extends UnitOfMeasure> List<T> saveAll(Iterable<T> categories) {
        return unitOfMeasureRepository.saveAll(categories);
    }

    @Override
    public void deleteById(Long id) {
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
