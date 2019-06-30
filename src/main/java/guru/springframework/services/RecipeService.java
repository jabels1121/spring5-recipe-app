package guru.springframework.services;


import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {
    
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator()
                .forEachRemaining(recipes::add);
        return recipes;
    }

    public Recipe getRecipeById(Long id) throws NoSuchElementException{
        Optional<Recipe> byId = recipeRepository.findById(id);
        return byId.orElseThrow();
    }

    public Recipe save(Recipe Recipe) {
        return recipeRepository.save(Recipe);
    }

    public <T extends Recipe> List<T> saveAll(Iterable<T> categories) {
        return recipeRepository.saveAll(categories);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public void deleteAll() {
        recipeRepository.deleteAll();
    }

    public void delete(Recipe Recipe) {
        recipeRepository.delete(Recipe);
    }

}
