package ma.emsi.serviceracines.service;
import ma.emsi.serviceracines.dto.PolynomeDTO;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RacineService {

    private static final Logger logger = LoggerFactory.getLogger(RacineService.class);

    @Value("${service.polynomes.url}")
    private String polynomesServiceUrl;

    private final RestTemplate restTemplate;

    public RacineService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String calculerRacines(String polynome) {
        try {
            logger.info("Calcul des racines pour le polynôme : {}", polynome);

            // Initialize Symja evaluator
            ExprEvaluator evaluator = new ExprEvaluator();

            // Prepare the Symja expression
            String symjaExpression = String.format("Solve(%s == 0, x)", polynome);
            logger.info("Symja expression: {}", symjaExpression);

            // Evaluate the Symja expression
            IExpr result = evaluator.evaluate(symjaExpression);
            logger.info("Symja result: {}", result);

            if (result != null && !result.toString().isEmpty()) {
                String racines = result.toString();
                logger.info("Racines calculées : {}", racines);

                // Send data to the Polynome Service
                envoyerAuServicePolynomes(polynome, racines);

                return racines;
            } else {
                logger.warn("Aucune racine trouvée pour le polynôme.");
                return "Aucune racine trouvée pour le polynôme.";
            }
        } catch (Exception e) {
            logger.error("Erreur lors du calcul des racines :", e);
            return "Une erreur est survenue lors du calcul des racines.";
        }
    }

    private void envoyerAuServicePolynomes(String polynome, String racines) {
        try {
            PolynomeDTO polynomeDTO = new PolynomeDTO(polynome, racines);

            String url = polynomesServiceUrl + "/polynomes/save";
            HttpEntity<PolynomeDTO> request = new HttpEntity<>(polynomeDTO);
            ResponseEntity<PolynomeDTO> response = restTemplate.postForEntity(url, request, PolynomeDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Polynôme sauvegardé avec succès dans le Service Polynômes.");
            } else {
                logger.error("Échec de la sauvegarde dans le Service Polynômes. Statut : {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi au Service Polynômes :", e);
        }
    }
}
