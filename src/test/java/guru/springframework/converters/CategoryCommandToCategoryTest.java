package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.entities.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private static final String NEW_DESCRIPTION = "New description";
    public static final String ID_VAL = "1";
    private CategoryCommandToCategory converter = new CategoryCommandToCategory();


    @Test
    void giveNullShouldReturnNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void giveEmptyFieldsCategoryCommandShouldReturnNotNullCategory() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        // given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VAL);
        categoryCommand.setDescription(NEW_DESCRIPTION);

        // when
        Category category = converter.convert(categoryCommand);

        // then
        assertAll("Category convert",
                () -> {
                    assertNotNull(category);

                    assertAll("Category props", () -> {
                        assertEquals(ID_VAL, category.getId());
                        assertEquals(NEW_DESCRIPTION, category.getDescription());
                        assertTrue(category.getRecipes().isEmpty());
                    });
                });
    }
}