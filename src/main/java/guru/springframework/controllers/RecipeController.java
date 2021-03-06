package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(path = "/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/{recipeId}/show")
    public String showRecipeById(@PathVariable String recipeId, Model model) {
        Recipe recipeById = recipeService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipeById);
        return "recipe/show";
    }

    @GetMapping(path = "/new")
    public ModelAndView newRecipe() {
        return new ModelAndView("recipe/recipeform", "recipe", new RecipeCommand());
    }

    @GetMapping(path = "/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @PostMapping(path = "")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand recipeCommand1 = recipeService.saveOrUpdate(recipeCommand);
        return "redirect:/recipe/" + recipeCommand1.getId() + "/show";
    }

    @GetMapping(path = "/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting id - " + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
