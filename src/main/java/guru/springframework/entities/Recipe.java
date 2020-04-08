package guru.springframework.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import guru.springframework.converters.CategorySetToCategoryDescriptionList;
import guru.springframework.serializers.IngredientSetSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Document
public class Recipe {

    @Id
    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    private String directions;

    private Difficulty difficulty;


    @JsonProperty("ingredients")
    @JsonSerialize(using = IngredientSetSerializer.class)
    private Set<Ingredient> ingredients = new HashSet<>();

    private byte[] image;

    private Notes notes;

    @DBRef
    @JsonSerialize(converter = CategorySetToCategoryDescriptionList.class)
    private Set<Category> categories = new HashSet<>();


    public Recipe addIngredient(Ingredient ingredient) {
        //ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        //notes.setRecipe(this);
    }
}
