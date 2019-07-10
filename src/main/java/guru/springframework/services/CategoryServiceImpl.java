package guru.springframework.services;

import guru.springframework.entities.Category;
import guru.springframework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getCategories() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().iterator()
                .forEachRemaining(categories::add);
        return categories;
    }

    @Override
    public Category findByDescription(String description) throws NoSuchElementException {
        Optional<Category> byDescription = categoryRepository.findByDescription(description);
        return byDescription.orElseThrow();
    }

    @Override
    public Category findById(Long id) throws NoSuchElementException{
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElseThrow();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public <T extends Category> List<T> saveAll(Iterable<T> categories) {
        return categoryRepository.saveAll(categories);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }


}
