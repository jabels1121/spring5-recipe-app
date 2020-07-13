package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.exceptions.RecipeNotFoundException;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findIngrCommandByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) throws RecipeNotFoundException;

    Mono<Void> deleteById(String recipeId, String idToDelete);

}
