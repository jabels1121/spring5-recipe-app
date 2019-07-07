package guru.springframework.services;


import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> byId = recipeRepository.findById(id);
        return byId.orElseThrow();
    }

    @Override
    public Recipe save(Recipe Recipe) {
        return recipeRepository.save(Recipe);
    }

    @Override
    public <T extends Recipe> List<T> saveAll(Iterable<T> categories) {
        return recipeRepository.saveAll(categories);
    }

    @Override
    public void deleteById(Long id) {
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

}
