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

            // Vérifier si le polynôme existe déjà dans la base
            PolynomeDTO existingPolynome = verifierPolynomeExistant(polynome);

            if (existingPolynome != null) {
                logger.info("Polynôme déjà existant : {}", existingPolynome);

                // Si les racines existent déjà, les retourner
                if (existingPolynome.getRacines() != null) {
                    return "Les racines existent déjà : " + existingPolynome.getRacines();
                }
            }

            // Calcul des racines avec Symja
            ExprEvaluator evaluator = new ExprEvaluator();
            String symjaExpression = String.format("Solve(%s == 0, x)", polynome);
            logger.info("Symja expression: {}", symjaExpression);

            IExpr result = evaluator.evaluate(symjaExpression);
            logger.info("Symja result: {}", result);

            if (result != null && !result.toString().isEmpty()) {
                String racines = result.toString();
                logger.info("Racines calculées : {}", racines);

                // Si le polynôme existe, mettre à jour ses racines
                if (existingPolynome != null) {
                    mettreAJourPolynome(existingPolynome.getExpression(), racines);
                } else {
                    // Sinon, créer un nouveau polynôme
                    envoyerAuServicePolynomes(polynome, racines);
                }

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

    private PolynomeDTO verifierPolynomeExistant(String expression) {
        try {
            String url = polynomesServiceUrl + "/polynomes/findByExpression?expression=" + expression;
            ResponseEntity<PolynomeDTO> response = restTemplate.getForEntity(url, PolynomeDTO.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.warn("Erreur lors de la vérification du polynôme existant :", e);
        }
        return null;
    }

    private void mettreAJourPolynome(String expression, String racines) {
        try {
            PolynomeDTO updatedPolynome = new PolynomeDTO(expression, racines);

            String url = polynomesServiceUrl + "/polynomes/update";
            HttpEntity<PolynomeDTO> request = new HttpEntity<>(updatedPolynome);
            ResponseEntity<PolynomeDTO> response = restTemplate.postForEntity(url, request, PolynomeDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Polynôme mis à jour avec succès dans le Service Polynômes.");
            } else {
                logger.error("Échec de la mise à jour du Polynôme. Statut : {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du Polynôme :", e);
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
