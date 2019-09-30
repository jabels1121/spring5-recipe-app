package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
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
}
