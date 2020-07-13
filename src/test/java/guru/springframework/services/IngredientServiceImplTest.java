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
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

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
        recipe.setId("1");

        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setDescription("first ingr");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("2");
        ingredient1.setDescription("second ingr");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("3");
        ingredient2.setDescription("third ingr");

        Arrays.asList(ingredient, ingredient1, ingredient2).forEach(recipe::addIngredient);
        IngredientToIngredientCommand converter =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(ingredientToIngredientCommand.convert(ingredient2))
                .thenReturn(converter.convert(ingredient2));

        var ingrCmd =
                ingredientService.findIngrCommandByRecipeIdAndIngredientId("1", "3").block();

        assertNotNull(ingrCmd);
        assertEquals("3", ingrCmd.getId());
        //assertEquals("1", ingrCmd.getRecipeId());
        assertEquals("third ingr", ingrCmd.getDescription());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    void saveNewIngredientCommand_shouldAddNewIngredientToRecipeAndReturnSavedIngredientCommand() throws RecipeNotFoundException {
        // given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("500");
        ingredientCommand.setAmount(BigDecimal.valueOf(3.5));
        ingredientCommand.setRecipeId("3");

        var recipe = new Recipe();
        recipe.setId("3");
        var ingredient = new Ingredient();
        recipe.addIngredient(ingredient);
        recipe.getIngredients().iterator().next().setId("500");

        IngredientCommandToIngredient converter =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        IngredientToIngredientCommand _converter =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        when(recipeReactiveRepository.findById("3")).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
        when(ingredientCommandToIngredient.convert(any(IngredientCommand.class)))
                .thenReturn(converter.convert(ingredientCommand));
        when(ingredientToIngredientCommand.convert(any(Ingredient.class)))
                .thenReturn(_converter.convert(ingredient));

        //when
        var savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertNotNull(savedIngredientCommand);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
        verify(ingredientCommandToIngredient, times(1)).convert(any(IngredientCommand.class));
        verify(ingredientToIngredientCommand, times(1)).convert(any(Ingredient.class));
    }

    @Test
    void saveIngredient_whenRecipeAlreadyHaveIngWithCorrespondingId_ShouldUpdateIngredient() throws RecipeNotFoundException {
        // given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("1");
        ingredientCommand.setRecipeId("3");
        ingredientCommand.setDescription("Blabla");
        ingredientCommand.setAmount(BigDecimal.valueOf(3.5));
        var uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId("3");
        uomCommand.setDescription("UomCmd description");
        ingredientCommand.setUom(uomCommand);

        var ingredient = new Ingredient();
        ingredient.setId("1");
        var uom = new UnitOfMeasure();
        uom.setId("3");
        uom.setDescription("Uom desciption");
        ingredient.setUom(uom);
        ingredient.setAmount(BigDecimal.valueOf(3.0));
        var recipe = new Recipe();
        //ingredient.setRecipe(recipe);
        recipe.setId("3");
        recipe.addIngredient(ingredient);

        when(recipeReactiveRepository.findById("3")).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
        when(ingredientToIngredientCommand.convert(any())).thenCallRealMethod();
        when(unitOfMeasureRepository.findById("3")).thenReturn(Optional.of(uom));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        //when
        var saveIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();

        // then
        assertNotNull(saveIngredientCommand);
        Ingredient updatedIngredient = recipe.getIngredients().stream().filter(ingredient1 -> ingredient.getId().equals("1")).findFirst().get();
        assertEquals("Blabla", updatedIngredient.getDescription());
        assertEquals(BigDecimal.valueOf(3.5), updatedIngredient.getAmount());
    }

    @Test
    void saveIngredient_toNotExistingRecipe_ShouldThrowRecipeNotFound() {
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("500");
        ingredientCommand.setAmount(BigDecimal.valueOf(3.5));
        ingredientCommand.setRecipeId("3");

        when(recipeReactiveRepository.findById("3")).thenReturn(Mono.empty());

        assertThrows(RecipeNotFoundException.class, () -> ingredientService.saveIngredientCommand(ingredientCommand));
    }
}