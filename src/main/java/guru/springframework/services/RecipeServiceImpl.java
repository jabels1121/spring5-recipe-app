package guru.springframework.services;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommand recipeToRecipeCommand,
                             RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator()
                .forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Set<RecipeCommand> getRecipeCommands() {
        Set<RecipeCommand> recipes = new HashSet<>();

        try {
            recipeRepository.findAll().forEach(e -> recipes.add(recipeToRecipeCommand.convert(e)));
        } catch (Exception e) {
            log.error("Cannot convert Set of Recipes to Set of RecipeCommands.", e.getMessage());
        }

        return recipes;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {
        return recipeToRecipeCommand.convert(getRecipeById(id));
    }

    @Override
    public Recipe getRecipeById(String id) {
        Optional<Recipe> byId = recipeRepository.findById(id);
        return byId.orElseThrow();
    }

    @Override
    public Recipe save(Recipe Recipe) {
        return recipeRepository.save(Recipe);
    }

    @Override
    public <T extends Recipe> Iterable<T> saveAll(Iterable<T> categories) {
        return recipeRepository.saveAll(categories);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        recipeRepository.deleteAll();
    }

    @Override
    public void delete(Recipe Recipe) {
        recipeRepository.delete(Recipe);
    }

    @Override
    public RecipeCommand saveOrUpdate(RecipeCommand recipeCommand) {
        Recipe convert = recipeCommandToRecipe.convert(recipeCommand);
        Recipe save = recipeRepository.save(convert);
        return recipeToRecipeCommand.convert(save);
    }
}
