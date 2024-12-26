package ma.emsi.servicefactorisation.service;

import com.emsi.servicefactorisation.service.FactorisationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactorisationServiceTest {

    private FactorisationService factorisationService;

    @BeforeEach
    void setUp() {
        factorisationService = new FactorisationService(null); // Le RestTemplate n'est pas utilisé ici
    }

    @Test
    void testFactorisationValide() {
        String input = "x^2 - 5x + 6";
        String expected = "(x - 2)*(x - 3)";
        String result = factorisationService.factoriserPolynome(input);
        assertEquals(expected, result, "La factorisation doit correspondre au résultat attendu.");
    }

    @Test
    void testPolynomeInvalide() {
        String input = "invalid_expression";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            factorisationService.factoriserPolynome(input);
        });
        assertTrue(exception.getMessage().contains("Erreur inattendue"));
    }

    @Test
    void testPolynomeNull() {
        String input = null;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            factorisationService.factoriserPolynome(input);
        });
        assertEquals("Le polynôme ne peut pas être nul ou vide.", exception.getMessage());
    }

    @Test
    void testPolynomeVide() {
        String input = "";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            factorisationService.factoriserPolynome(input);
        });
        assertEquals("Le polynôme ne peut pas être nul ou vide.", exception.getMessage());
    }
}
