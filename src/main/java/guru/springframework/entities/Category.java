package guru.springframework.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import guru.springframework.converters.RecipeSetToRecipeIdList;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @JsonSerialize(converter = RecipeSetToRecipeIdList.class)
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();

}