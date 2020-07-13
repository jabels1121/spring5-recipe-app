package guru.springframework.repositories.reactive;

import guru.springframework.entities.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class})
@DataMongoTest
public class RecipeReactiveRepositoryTestIT {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    public void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    void testRecipeSave() {
        var recipe = new Recipe();
        recipe.setDescription("Test recipe");

        recipeReactiveRepository.save(recipe).block();

        var count = recipeReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
        assertNotNull(recipe.getId());
    }
}
