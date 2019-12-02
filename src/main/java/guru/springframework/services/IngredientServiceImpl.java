package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.entities.Ingredient;
import guru.springframework.entities.Recipe;
import guru.springframework.exceptions.RecipeNotFoundException;
import guru.springframework.exceptions.UnitOfMeasureNotFound;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findIngrCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> byId = recipeRepository.findById(recipeId);

        if (!byId.isPresent()) {
            log.error("Recipe with id - " + recipeId + " doesn't exist!");
        }
        Recipe recipe = byId.get();

        Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream()
                .filter(ingr -> ingr.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if (!ingredientCommand.isPresent()) {
            log.error("Ingredient with id - " + ingredientId + " doesn't exist!");
        }

        return ingredientCommand.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(final IngredientCommand command) throws RecipeNotFoundException {
        var recipeId = command.getRecipeId();
        var recipeOptional = recipeRepository.findById(recipeId);

        var recipe = recipeOptional.orElseThrow(() -> {
            log.error("Recipe not found with id = " + recipeId);
            return new RecipeNotFoundException("Recipe not found with id = " + recipeId);
        });

        recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .ifPresentOrElse(ingredient -> {
                            ingredient.setAmount(command.getAmount());
                            ingredient.setDescription(command.getDescription());
                            ingredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                                    .orElseThrow(() -> {
                                        log.error("UOM with id = " + command.getUom().getId() +
                                                " NOT FOUND!");
                                        return new UnitOfMeasureNotFound("UOM with id = " + command.getUom().getId() +
                                                " NOT FOUND!");
                                    }));
                        },
                        () -> {
                            log.info("Add new ingredient={} to recipe={}", command, recipe);
                            recipe.addIngredient(Objects.requireNonNull(ingredientCommandToIngredient.convert(command)));
                        }
                );

        var savedRecipe = recipeRepository.save(recipe);

        var savedIngredient = savedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not found!"));

        return ingredientToIngredientCommand.convert(savedIngredient);
    }
}
