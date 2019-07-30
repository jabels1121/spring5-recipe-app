package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/{recipeId}/show")
    public String showRecipeById(@PathVariable Long recipeId, Model model) {
        Recipe recipeById = recipeService.getRecipeById(recipeId);

        model.addAttribute("recipe", recipeById);

        return "recipe/show";
    }

    @GetMapping(path = "/new")
    public ModelAndView newRecipe() {
        return new ModelAndView("recipe/recipeform", "recipe", new RecipeCommand());
    }

    @PostMapping(path = "")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand recipeCommand1 = recipeService.saveOrUpdate(recipeCommand);
        return "redirect:/recipe/" + recipeCommand1.getId() + "/show";
    }
}
