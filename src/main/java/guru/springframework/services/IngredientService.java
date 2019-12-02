package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.exceptions.RecipeNotFoundException;

public interface IngredientService {

    IngredientCommand findIngrCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command) throws RecipeNotFoundException;

}
