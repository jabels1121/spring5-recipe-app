package guru.springframework.controllers;

import guru.springframework.entities.Recipe;
import guru.springframework.services.RecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class IndexControllerTest {

    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    Model model;

    @InjectMocks
    IndexController indexController;


    @Test
    public void testMockMvc() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void indexPage() {
        //given
        Recipe recipe = new Recipe();
        Recipe recipe1 = new Recipe();
        recipe.setId("1");
        recipe1.setId("2");
        Set<Recipe> recipes = new HashSet<>(Arrays.asList(recipe, recipe1));

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        String viewName = indexController.indexPage(model);

        // then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        assertEquals(recipes, argumentCaptor.getValue());
    }
}