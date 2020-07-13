package guru.springframework.services;

import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.entities.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

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
        var unitOfMeasures =
               Flux.just(new UnitOfMeasure(), new UnitOfMeasure());

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(unitOfMeasures);

        // when
        var all = unitOfMeasureService.findAll().collectList().block();

        // then
        assertAll("unitOfMeasureService.findAll() assertions", () -> {
            assertNotNull(all);
            assertEquals(2, (int) StreamSupport.stream(all.spliterator(), false).count());
            verify(unitOfMeasureReactiveRepository, times(1)).findAll();
        });
    }

    @Test
    void findById_happyPath() {
        // given
        String id = "1";
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(id);

        when(unitOfMeasureRepository.findById("1")).thenReturn(Optional.of(uom));

        // when
        UnitOfMeasure unitOfMeasure = unitOfMeasureService.findById("1");

        // then
        assertAll("unitOfMeasureService.findById(1L) assertions", () -> {
            assertNotNull(unitOfMeasure);
            assertEquals(id, unitOfMeasure.getId());
        });
    }

    @Test
    void findById_throwsNoSuchElementException() {
        // given
        when(unitOfMeasureRepository.findById("1")).thenReturn(Optional.empty());

        // when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> {
            unitOfMeasureService.findById("1");
        }, "No value present");
    }

    @Test
    void listAllUomCommands() {
        String id = "1";
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(id);

        String id2 = "2";
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom.setId(id2);

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom, uom1));
        when(unitOfMeasureToUnitOfMeasureCommand.convert(any())).thenCallRealMethod();

        //when
        var unitOfMeasureCommands = unitOfMeasureService.listAllUomCommands().collectList().block();

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