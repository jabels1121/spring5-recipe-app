package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Set<RecipeCommand> getRecipeCommands();

    Recipe getRecipeById(Long id) throws NoSuchElementException;

    RecipeCommand findCommandById(Long id);

    Recipe save(Recipe Recipe);

    <T extends Recipe> List<T> saveAll(Iterable<T> categories);

    void deleteById(Long id);

    void deleteAll();

    void delete(Recipe Recipe);

    RecipeCommand saveOrUpdate(RecipeCommand recipeCommand);
}
