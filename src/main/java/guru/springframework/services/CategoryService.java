package guru.springframework.services;

import guru.springframework.entities.Category;

import java.util.NoSuchElementException;
import java.util.Set;

public interface CategoryService {

    Set<Category> getCategories();

    Category findByDescription(String description) throws NoSuchElementException;

    Category findById(String id) throws NoSuchElementException;

    Category save(Category category);

    <T extends Category> Iterable<T> saveAll(Iterable<T> categories);

    void deleteById(String id);

    void deleteAll();

    void delete(Category category);

}
