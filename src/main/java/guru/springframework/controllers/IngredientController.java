package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping(path = "/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) {
        log.debug("Getting ingredient list for recipe id: " + id);

        RecipeCommand commandById = recipeService.findCommandById(id);
        model.addAttribute("recipe", commandById);

        return "recipe/ingredient/list";
    }

    @GetMapping(path = "/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId,
                                       Model model) {
        IngredientCommand ingrCommandByRecipeIdAndIngredientId
                = ingredientService.findIngrCommandByRecipeIdAndIngredientId(recipeId, ingredientId);
        model.addAttribute("ingredient", ingrCommandByRecipeIdAndIngredientId);
        return "recipe/ingredient/show";
    }

    @GetMapping(path = "/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId,
                                         Model model) {
        var commandById = recipeService.findCommandById(recipeId);
        commandById.getIngredients().removeIf(ingredientCommand -> ingredientCommand.getId().equalsIgnoreCase(ingredientId));
        recipeService.saveOrUpdate(commandById);
        model.addAttribute("recipe", commandById);
        return "recipe/ingredient/list";
    }

    @GetMapping(path = "/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId,
                                         Model model) {
        var ingr = recipeService.findCommandById(recipeId)
                .getIngredients().stream().filter(ingredientCommand -> ingredientCommand.getId().equalsIgnoreCase(ingredientId))
                .findFirst().orElseThrow();
        model.addAttribute("ingredient", ingr);
        return "recipe/ingredient/ingredientform";
    }

}
