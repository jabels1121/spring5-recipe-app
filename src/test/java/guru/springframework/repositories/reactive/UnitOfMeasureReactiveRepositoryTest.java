package guru.springframework.repositories.reactive;

import guru.springframework.entities.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    private static final String EACH = "Each";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    void UOMSaveTest() {
        var unitOfMeasure = saveNewUom(EACH);

        assertEquals(Long.valueOf(1L), unitOfMeasureReactiveRepository.count().block());
        assertNotNull(unitOfMeasure.getId());
    }

    @Test
    void findByDescriptionTest() {
        saveNewUom(EACH);

        var uom = unitOfMeasureReactiveRepository.findByDescription(EACH).block();

        assertNotNull(uom);
    }

    private UnitOfMeasure saveNewUom(final String uomDescription) {
        var unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(uomDescription);

        return unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
    }
}