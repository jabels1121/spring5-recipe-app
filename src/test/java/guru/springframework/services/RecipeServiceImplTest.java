package guru.springframework.services;

import guru.springframework.entities.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeServiceImpl;


    @Test
    public void getRecipesTest() {
        Recipe recipe = new Recipe();
        List<Recipe> recipesData = new ArrayList<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeServiceImpl.getRecipes();

        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeById = recipeServiceImpl.getRecipeById(1L);

        assertNotNull(recipeById, "Null recipe returned");
        assertEquals(recipeById.getId(), recipe.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void testDeleteById() {
        Long idToDelete = 2L;
        recipeServiceImpl.deleteById(idToDelete);
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}