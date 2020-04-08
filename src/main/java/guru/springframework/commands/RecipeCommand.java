package guru.springframework.commands;

import guru.springframework.entities.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private List<CategoryCommand> categories = new ArrayList<>();
}
