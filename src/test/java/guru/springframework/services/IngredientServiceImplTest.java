package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.Ingredient;
import guru.springframework.entities.Recipe;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.exceptions.RecipeNotFoundException;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
    IngredientToIngredientCommand ingredientToIngredientCommand =
            new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient =
            new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

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
        assertEquals(3L, (long) ingrCmd.getId());
        assertEquals(1L, (long) ingrCmd.getRecipeId());
        assertEquals("third ingr", ingrCmd.getDescription());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveNewIngredientCommand_shouldAddNewIngredientToRecipeAndReturnSavedIngredientCommand() throws RecipeNotFoundException {
        // given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(500L);
        ingredientCommand.setAmount(BigDecimal.valueOf(3.5));
        ingredientCommand.setRecipeId(3L);

        var recipeOptional = Optional.of(new Recipe());
        var recipe = new Recipe();
        recipe.setId(3L);
        var ingredient = new Ingredient();
        recipe.addIngredient(ingredient);
        recipe.getIngredients().iterator().next().setId(500L);

        IngredientCommandToIngredient converter =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        IngredientToIngredientCommand _converter =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        when(recipeRepository.findById(3L)).thenReturn(recipeOptional);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(ingredientCommandToIngredient.convert(any(IngredientCommand.class)))
                .thenReturn(converter.convert(ingredientCommand));
        when(ingredientToIngredientCommand.convert(any(Ingredient.class)))
                .thenReturn(_converter.convert(ingredient));

        //when
        var savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertNotNull(savedIngredientCommand);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(ingredientCommandToIngredient, times(1)).convert(any(IngredientCommand.class));
        verify(ingredientToIngredientCommand, times(1)).convert(any(Ingredient.class));
    }

    @Test
    void saveIngredient_whenRecipeAlreadyHaveIngWithCorrespondingId_ShouldUpdateIngredient() throws RecipeNotFoundException {
        // given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(3L);
        ingredientCommand.setDescription("Blabla");
        ingredientCommand.setAmount(BigDecimal.valueOf(3.5));
        var uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(3L);
        uomCommand.setDescription("UomCmd description");
        ingredientCommand.setUom(uomCommand);

        var ingredient = new Ingredient();
        ingredient.setId(1L);
        var uom = new UnitOfMeasure();
        uom.setId(3L);
        uom.setDescription("Uom desciption");
        ingredient.setUom(uom);
        ingredient.setAmount(BigDecimal.valueOf(3.0));
        var recipe = new Recipe();
        ingredient.setRecipe(recipe);
        recipe.setId(3L);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(3L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(ingredientToIngredientCommand.convert(any())).thenCallRealMethod();
        when(unitOfMeasureRepository.findById(3L)).thenReturn(Optional.of(uom));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        //when
        var saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        // then
        assertNotNull(saveIngredientCommand);
        Ingredient updatedIngredient = recipe.getIngredients().stream().filter(ingredient1 -> ingredient.getId().equals(1L)).findFirst().get();
        assertEquals("Blabla", updatedIngredient.getDescription());
        assertEquals(BigDecimal.valueOf(3.5), updatedIngredient.getAmount());
    }

    @Test
    void saveIngredient_toNotExistingRecipe_ShouldThrowRecipeNotFound() {
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(500L);
        ingredientCommand.setAmount(BigDecimal.valueOf(3.5));
        ingredientCommand.setRecipeId(3L);

        when(recipeRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> ingredientService.saveIngredientCommand(ingredientCommand));
    }
}