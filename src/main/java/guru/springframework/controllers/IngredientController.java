package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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
        var ingrCommandByRecipeIdAndIngredientId
                = ingredientService.findIngrCommandByRecipeIdAndIngredientId(recipeId, ingredientId).block();
        model.addAttribute("ingredient", ingrCommandByRecipeIdAndIngredientId);
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        //todo raise exception if null
        if (recipeCommand == null) throw new IllegalArgumentException("Wrong recipeId value");

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUomCommands().collectList().block());

        return "recipe/ingredient/ingredientform";
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

        model.addAttribute("uomList", unitOfMeasureService.listAllUomCommands().collectList().block());

        return "recipe/ingredient/ingredientform";
    }


    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        var savedCommand = ingredientService.saveIngredientCommand(command).block();

        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + command.getRecipeId() + "/ingredients";
        //return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

}
