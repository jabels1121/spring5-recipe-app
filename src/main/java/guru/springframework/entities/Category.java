package guru.springframework.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import guru.springframework.converters.RecipeSetToRecipeIdList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Document
public class Category {

    @Id
    private String id;
    private String description;

    @JsonSerialize(converter = RecipeSetToRecipeIdList.class)
    private Set<Recipe> recipes = new HashSet<>();

    public static List<Category> createCollectionOfCategoriesWithoutId(String... description) {
        return Arrays.stream(description).map(s -> {
            var category = new Category();
            category.setDescription(s);
            return category;
        }).collect(Collectors.toList());
    }

}
