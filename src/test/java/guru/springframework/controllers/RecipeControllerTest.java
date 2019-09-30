package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.entities.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void showRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void showFormForNewRecipe() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void updateRecipe_ShouldReturnRecipeFormWithFoundRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription("testtest");
        recipe.setCookTime(20);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        recipeCommand.setDescription("testtest");
        recipeCommand.setCookTime(20);

        when(recipeService.findCommandById(1L)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", hasProperty("description", is("testtest"))))
                .andExpect(model().attribute("recipe", hasProperty("cookTime", is(20))))
                .andExpect(model().attribute("recipe", hasProperty("id", is(1L))));
    }

    @Test
    void saveOrUpdate() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        recipeCommand.setDescription("testtest");

        when(recipeService.saveOrUpdate(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "testtest"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeCommand.getId() + "/show"));
    }

    @Test
    void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService, times(1)).deleteById(anyLong());
    }
}