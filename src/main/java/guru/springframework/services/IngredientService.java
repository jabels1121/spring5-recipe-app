package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngrCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
