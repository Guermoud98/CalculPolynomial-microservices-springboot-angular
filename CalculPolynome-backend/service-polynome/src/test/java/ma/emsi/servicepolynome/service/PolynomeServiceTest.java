package ma.emsi.servicepolynome.service;

import ma.emsi.servicepolynome.entity.Polynome;
import ma.emsi.servicepolynome.repository.PolynomeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolynomeServiceTest {

    @InjectMocks
    private PolynomeService polynomeService;

    @Mock
    private PolynomeRepository polynomeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePolynome() {
        Polynome polynome = new Polynome();
        polynome.setExpression("x^2 - x");

        when(polynomeRepository.save(polynome)).thenReturn(polynome);

        Polynome savedPolynome = polynomeService.savePolynome(polynome);

        assertNotNull(savedPolynome);
        assertEquals("x^2 - x", savedPolynome.getExpression());
        verify(polynomeRepository, times(1)).save(polynome);
    }

    @Test
    void testFindByExpression() {
        Polynome polynome = new Polynome();
        polynome.setExpression("x^2 - x");

        when(polynomeRepository.findByExpression("x^2 - x")).thenReturn(polynome);

        Polynome foundPolynome = polynomeService.findByExpression("x^2 - x");

        assertNotNull(foundPolynome);
        assertEquals("x^2 - x", foundPolynome.getExpression());
        verify(polynomeRepository, times(1)).findByExpression("x^2 - x");
    }

    @Test
    void testUpdatePolynome() {
        Polynome existingPolynome = new Polynome();
        existingPolynome.setId(1L);
        existingPolynome.setExpression("x^2 - x");

        Polynome updatedPolynome = new Polynome();
        updatedPolynome.setId(1L);
        updatedPolynome.setExpression("x^3 - x");

        when(polynomeRepository.findById(1L)).thenReturn(Optional.of(existingPolynome));
        when(polynomeRepository.save(existingPolynome)).thenReturn(updatedPolynome);

        Polynome result = polynomeService.updatePolynome(updatedPolynome);

        assertNotNull(result);
        assertEquals("x^3 - x", result.getExpression());
        verify(polynomeRepository, times(1)).findById(1L);
        verify(polynomeRepository, times(1)).save(existingPolynome);
    }

    @Test
    void testGetAllPolynomes() {
        Polynome polynome1 = new Polynome();
        polynome1.setExpression("x^2 - x");

        Polynome polynome2 = new Polynome();
        polynome2.setExpression("x^3 - x");

        List<Polynome> polynomes = Arrays.asList(polynome1, polynome2);

        when(polynomeRepository.findAll()).thenReturn(polynomes);

        List<Polynome> result = polynomeService.getAllPolynomes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(polynomeRepository, times(1)).findAll();
    }

    @Test
    void testGetPolynomeById() {
        Polynome polynome = new Polynome();
        polynome.setId(1L);
        polynome.setExpression("x^2 - x");

        when(polynomeRepository.findById(1L)).thenReturn(Optional.of(polynome));

        Polynome result = polynomeService.getPolynomeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("x^2 - x", result.getExpression());
        verify(polynomeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPolynomeById_NotFound() {
        when(polynomeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            polynomeService.getPolynomeById(1L);
        });

        assertEquals("Polynôme introuvable avec l'ID : 1", exception.getMessage());
        verify(polynomeRepository, times(1)).findById(1L);
    }
}
