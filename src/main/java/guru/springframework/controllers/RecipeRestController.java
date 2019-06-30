package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RecipeRestController extends AbstractRestController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public RecipeRestController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }


    @GetMapping(path = "/recipes")
    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping(path = {"/uom"})
    public List<UnitOfMeasure> getAllUom() {
        return unitOfMeasureRepository.findAll();
    }

    @GetMapping(path = "/uom/{description}")
    public UnitOfMeasure getUomByDescription(@PathVariable String description) {
        Optional<UnitOfMeasure> byDescription = unitOfMeasureRepository.findByDescription(description);
        return byDescription.orElse(null);
    }
}
