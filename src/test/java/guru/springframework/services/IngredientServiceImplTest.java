package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.Ingredient;
import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findIngrCommandByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setDescription("first ingr");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);
        ingredient1.setDescription("second ingr");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);
        ingredient2.setDescription("third ingr");

        Arrays.asList(ingredient, ingredient1, ingredient2).forEach(recipe::addIngredient);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        IngredientToIngredientCommand converter =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        when(ingredientToIngredientCommand.convert(ingredient2))
                .thenReturn(converter.convert(ingredient2));

        IngredientCommand ingrCmd =
                ingredientService.findIngrCommandByRecipeIdAndIngredientId(1L, 3L);

        assertNotNull(ingrCmd);
        assertEquals(3L, (long)ingrCmd.getId());
        assertEquals(1L, (long)ingrCmd.getRecipeId());
        assertEquals("third ingr", ingrCmd.getDescription());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
}