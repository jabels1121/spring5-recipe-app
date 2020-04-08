package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;

import java.util.NoSuchElementException;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Set<RecipeCommand> getRecipeCommands();

    Recipe getRecipeById(String id) throws NoSuchElementException;

    RecipeCommand findCommandById(String id);

    Recipe save(Recipe Recipe);

    <T extends Recipe> Iterable<T> saveAll(Iterable<T> categories);

    void deleteById(String id);

    void deleteAll();

    void delete(Recipe Recipe);

    RecipeCommand saveOrUpdate(RecipeCommand recipeCommand);
}
