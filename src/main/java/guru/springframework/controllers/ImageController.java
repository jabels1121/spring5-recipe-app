package guru.springframework.controllers;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(final RecipeService recipeService,
                           final ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping(path = "/recipe/{recipeId}/image")
    public String getImageForm(@PathVariable final Long recipeId, final Model model) {
        var byId = recipeService.findCommandById(recipeId);

        if (null != byId) {

            model.addAttribute("recipe", byId);
            return "recipe/imageuploadform";
        } else throw new RuntimeException("Recipe with id=" + recipeId + " not found!");
    }
}
