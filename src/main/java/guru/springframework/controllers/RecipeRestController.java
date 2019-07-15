package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class RecipeRestController extends AbstractRestController {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;

    public RecipeRestController(UnitOfMeasureRepository unitOfMeasureRepository,
                                RecipeService recipeService) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/recipes")
    public Set<Recipe> findAllRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping(path = "/recipeCommands")
    public Set<RecipeCommand> findAllRecipeCommands() {
        return recipeService.getRecipeCommands();
    }

    @GetMapping(path = "/recipe/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
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
