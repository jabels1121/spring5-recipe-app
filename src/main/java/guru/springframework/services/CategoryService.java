package guru.springframework.services;

import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<Category> getCategories() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().iterator()
                .forEachRemaining(categories::add);
        return categories;
    }

    public Category findByDescription(String description) throws NoSuchElementException {
        Optional<Category> byDescription = categoryRepository.findByDescription(description);
        return byDescription.orElseThrow();
    }

    public Category findById(Long id) throws NoSuchElementException{
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElseThrow();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public <T extends Category> List<T> saveAll(Iterable<T> categories) {
        return categoryRepository.saveAll(categories);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }


}
