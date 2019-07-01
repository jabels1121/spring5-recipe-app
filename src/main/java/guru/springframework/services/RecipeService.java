package guru.springframework.services;

import guru.springframework.domain.Recipe;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe getRecipeById(Long id) throws NoSuchElementException;

    Recipe save(Recipe Recipe);

    <T extends Recipe> List<T> saveAll(Iterable<T> categories);

    void deleteById(Long id);

    void deleteAll();

    void delete(Recipe Recipe);
}
