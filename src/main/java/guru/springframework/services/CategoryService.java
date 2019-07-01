package guru.springframework.services;

import guru.springframework.domain.Category;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public interface CategoryService {
    Set<Category> getCategories();

    Category findByDescription(String description) throws NoSuchElementException;

    Category findById(Long id) throws NoSuchElementException;

    Category save(Category category);

    <T extends Category> List<T> saveAll(Iterable<T> categories);

    void deleteById(Long id);

    void deleteAll();

    void delete(Category category);
}
