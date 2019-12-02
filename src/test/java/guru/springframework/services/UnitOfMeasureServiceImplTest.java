package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        // given
        List<UnitOfMeasure> unitOfMeasures =
                Arrays.asList(new UnitOfMeasure(), new UnitOfMeasure());

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        // when
        List<UnitOfMeasure> all = unitOfMeasureService.findAll();

        // then
        assertAll("unitOfMeasureService.findAll() assertions", () -> {
            assertNotNull(all);
            assertEquals(2, all.size());
            verify(unitOfMeasureRepository, times(1)).findAll();
        });
    }

    @Test
    void findById_happyPath() {
        // given
        Long id = 1L;
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(id);

        when(unitOfMeasureRepository.findById(1L)).thenReturn(Optional.of(uom));

        // when
        UnitOfMeasure unitOfMeasure = unitOfMeasureService.findById(1L);

        // then
        assertAll("unitOfMeasureService.findById(1L) assertions", () -> {
            assertNotNull(unitOfMeasure);
            assertEquals(id, unitOfMeasure.getId());
        });
    }

    @Test
    void findById_throwsNoSuchElementException() {
        // given
        when(unitOfMeasureRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            unitOfMeasureService.findById(1L);
        }, "No value present");
    }

    @Test
    void listAllUomCommands() {
        Long id = 1L;
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(id);

        Long id2 = 2L;
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom.setId(id2);

        when(unitOfMeasureRepository.findAll()).thenReturn(Arrays.asList(uom, uom1));
        when(unitOfMeasureToUnitOfMeasureCommand.convert(any())).thenCallRealMethod();

        //when
        var unitOfMeasureCommands = unitOfMeasureService.listAllUomCommands();

        assertNotNull(unitOfMeasureCommands);
        assertEquals(2, unitOfMeasureCommands.size());
    }

    @Test
    void save() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void delete() {
    }
}