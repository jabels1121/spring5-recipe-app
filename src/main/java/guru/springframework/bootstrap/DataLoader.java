package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private void createRecipes() {
        Recipe chickenTacos = new Recipe();
        chickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        chickenTacos.setPrepTime(20);
        chickenTacos.setCookTime(15);
        chickenTacos.setServings(5);
        chickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacos.setDifficulty(Difficulty.MODERATE);
        chickenTacos.setImage(getBytesFromImg("static/recipe_img/2017-05-29-GrilledChickenTacos-2.jpg"));

        Category mex = categoryRepository.findByDescription("Mexican").orElse(null);
        Category fastFood = categoryRepository.findByDescription("Fast Food").orElse(null);

        chickenTacos.setCategories(Stream.of(mex, fastFood).collect(Collectors.toSet()));

        Notes chickenTacosNote = new Notes();
        chickenTacosNote.setRecipe(chickenTacos);
        chickenTacosNote.setRecipeNotes(getChickenTacosNotes());
        chickenTacos.setNotes(chickenTacosNote);


    }

    private Set<Ingredient> getChickenTacosIngredient(Recipe recipe) {

        Ingredient anchoChiliPowder = new Ingredient();
        anchoChiliPowder.setAmount(new BigDecimal(2));
        anchoChiliPowder.setDescription("ancho chili powder");
        anchoChiliPowder.setRecipe(recipe);
        anchoChiliPowder.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").orElse(null));

        Ingredient driedOregano = new Ingredient();
        driedOregano.setRecipe(recipe);
        driedOregano.setDescription("dried oregano");
        driedOregano.setAmount(new BigDecimal(1));
        anchoChiliPowder.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").orElse(null));

        return null;

    }

    private byte[] getBytesFromImg(String filePath) {
        byte[] bytes = null;
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream(filePath);
            bytes = IOUtils.toByteArray(resourceAsStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return bytes;
    }

    private String getChickenTacosNotes() {
        return "METHODHIDE PHOTOS\n" +
                "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.";
    }
}
