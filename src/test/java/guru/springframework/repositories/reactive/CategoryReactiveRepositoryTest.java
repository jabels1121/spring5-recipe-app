package guru.springframework.repositories.reactive;

import guru.springframework.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@DataMongoTest
class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    void findByDescription() {
        var category = new Category();
        var test_category = "Test category";
        category.setDescription(test_category);

        categoryReactiveRepository.save(category).block();

        var category1 = categoryReactiveRepository.findByDescription(test_category).block();

        assertNotNull(category1);
    }
}