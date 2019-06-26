package guru.springframework.bootstrap;

import guru.springframework.dao.CategoryRepository;
import guru.springframework.dao.RecipeRepository;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category veg = new Category();
        veg.setDescription("Vegetarians");
        Category judes = new Category();
        judes.setDescription("Jude's meal");
        Category halal = new Category();
        halal.setDescription("Allah akbar!");
        Category normal = new Category();
        normal.setDescription("Normal");

        Recipe vegRec = new Recipe();
        Recipe meatRec = new Recipe();
        Recipe carbonarra = new Recipe();
        Recipe raviolli = new Recipe();

        judes.getRecipes().addAll(Arrays.asList(vegRec, raviolli));
        halal.getRecipes().addAll(Arrays.asList(meatRec, vegRec));

        normal.getRecipes().addAll(Arrays.asList(meatRec, carbonarra, raviolli, vegRec));
        veg.getRecipes().add(vegRec);

        vegRec.getCategories().add(veg);
        vegRec.getCategories().add(halal);

        meatRec.getCategories().add(judes);
        meatRec.getCategories().add(normal);

        carbonarra.getCategories().add(normal);
        carbonarra.getCategories().add(judes);

        raviolli.getCategories().add(halal);
        raviolli.getCategories().add(normal);

        Arrays.asList(veg, halal, judes,normal)
                .forEach(categoryRepository::save);

        Arrays.asList(vegRec, carbonarra, meatRec, raviolli)
                .forEach(recipeRepository::save);

    }
}
