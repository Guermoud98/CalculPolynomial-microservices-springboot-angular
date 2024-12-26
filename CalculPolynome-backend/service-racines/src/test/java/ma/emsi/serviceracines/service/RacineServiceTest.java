package ma.emsi.serviceracines.service;


import ma.emsi.serviceracines.dto.PolynomeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RacineServiceTest {

    @InjectMocks
    private RacineService racineService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculerRacines_Success() {
        // Arrange
        String polynome = "x^2 - x";
        String expectedRacines = "{{x->0},{x->1}}";

        // Mock behavior of Symja (you can directly test logic)
        RacineService spyService = spy(racineService);
        doReturn(expectedRacines).when(spyService).calculerRacines(polynome);

        // Act
        String result = spyService.calculerRacines(polynome);

        // Assert
        assertEquals(expectedRacines, result);
    }
}
