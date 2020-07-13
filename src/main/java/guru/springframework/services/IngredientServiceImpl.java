package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.exceptions.RecipeNotFoundException;
import guru.springframework.exceptions.UnitOfMeasureNotFound;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository uomReactiveRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureReactiveRepository uomReactiveRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.uomReactiveRepository = uomReactiveRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findIngrCommandByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeReactiveRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    var ingrCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingrCommand.setRecipeId(recipeId);
                    return ingrCommand;
                });

        /*map(recipe -> recipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                        .findFirst())
                .filter(Optional::isPresent)
                .map(ingredient -> {
                    var ingrCommand = ingredientToIngredientCommand.convert(ingredient.get());
                    ingrCommand.setRecipeId(recipeId);
                    return ingrCommand;
                });*/


        /*Optional<Recipe> byId = recipeReactiveRepository.findById(recipeId);

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

        var result = ingredientCommand.get();
        result.setRecipeId(recipeId);
        return Mono.just(result);*/
    }

    @Transactional
    @Override
    public Mono<IngredientCommand>  saveIngredientCommand(final IngredientCommand command)
            throws RecipeNotFoundException {
        var recipeId = command.getRecipeId();
        var recipeOptional = recipeReactiveRepository.findById(recipeId).blockOptional();

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
                            ingredient.setRecipeId(command.getRecipeId());
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

        var savedRecipe = recipeReactiveRepository.save(recipe).blockOptional();

        if (savedRecipe.isPresent()) {
            var savedIngredient = savedRecipe.get()
                .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Ingredient not found!"));
            var convert = ingredientToIngredientCommand.convert(savedIngredient);
            return Mono.just(convert);
        } else throw new RuntimeException("Recipe doesn't save");
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String idToDelete) {
        recipeReactiveRepository.findById(recipeId).blockOptional()
                .ifPresentOrElse(recipe -> {
                            recipe.getIngredients()
                                    .removeIf(ingredient -> ingredient.getId().equalsIgnoreCase(idToDelete));
                            recipeReactiveRepository.save(recipe);
                        },
                        () -> {
                            log.error("Not found recipe with id={}", recipeId);
                            throw new IllegalArgumentException("Not found recipe with id=" + recipeId);
                        });
        return Mono.empty();
    }
}
