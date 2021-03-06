package guru.springframework.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import guru.springframework.entities.Recipe;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeSetToRecipeIdList extends StdConverter<Set<Recipe>, List<String>> {


    @Override
    public List<String> convert(Set<Recipe> recipes) {
        return recipes.stream().map(Recipe::getId).collect(Collectors.toList());
    }
}
