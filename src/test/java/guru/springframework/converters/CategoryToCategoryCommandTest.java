package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public static final String ID_VAL = "1";
    public static final String NEW_DESCRIPTION = "New description";
    private CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        // given
        Category category = new Category();
        category.setId(ID_VAL);
        category.setDescription(NEW_DESCRIPTION);

        // when
        CategoryCommand categoryCommand = converter.convert(category);

        // then
        assertAll("CategoryCommand convert", () -> {
            assertNotNull(categoryCommand);

            assertAll("CategoryCommand props", () -> {
                assertEquals(ID_VAL, categoryCommand.getId());
                assertEquals(NEW_DESCRIPTION, categoryCommand.getDescription());
            });
        });
    }
}