package ma.emsi.serviceracines.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.emsi.serviceracines.dto.PolynomeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RacineService {

    private static final Logger logger = LoggerFactory.getLogger(RacineService.class);

    @Value("${wolframalpha.api.key}")
    private String apiKey;

    @Value("${wolframalpha.api.url}")
    private String apiUrl;

    @Value("${service.polynomes.url}")
    private String polynomesServiceUrl;

    private final RestTemplate restTemplate;

    public RacineService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Calcul des racines d'un polynôme via l'API LLM de Wolfram.
     * @param polynome L'expression du polynôme à résoudre.
     * @return Les racines sous forme de texte ou un message d'erreur.
     */
    public String calculerRacines(String polynome) {
        try {
            // Construire l'URL de requête pour Wolfram Alpha LLM API
            String url = String.format("%s?appid=%s&input=%s", apiUrl, apiKey, polynome);

            logger.info("Envoi de la requête vers Wolfram Alpha LLM API : {}", url);

            // Appel API
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Réponse API reçue avec succès.");
                logger.info("Réponse brute API : {}", response.getBody());

                String racines = extraireRacines(response.getBody());

                // Envoi des données au Service Polynômes
                envoyerAuServicePolynomes(polynome, racines);

                return racines;
            } else {
                logger.error("Échec de l'appel API. Statut : {}", response.getStatusCode());
                return "Erreur lors de l'appel à l'API Wolfram Alpha LLM.";
            }

        } catch (Exception e) {
            logger.error("Erreur lors de l'appel ou du traitement de Wolfram Alpha LLM API", e);
            return "Une erreur est survenue lors du calcul des racines.";
        }
    }


    private String extraireRacines(String response) {
        StringBuilder racines = new StringBuilder();
        Pattern realRootPattern = Pattern.compile("Real root:\\s*(x = [^\\n]+)");
        Pattern complexRootsPattern = Pattern.compile("Complex roots:\\s*((x = [^\\n]+\\n?)+)");

        Matcher realRootMatcher = realRootPattern.matcher(response);
        if (realRootMatcher.find()) {
            racines.append("Real root: ").append(realRootMatcher.group(1)).append("\n");
        }

        Matcher complexRootsMatcher = complexRootsPattern.matcher(response);
        if (complexRootsMatcher.find()) {
            racines.append("Complex roots: ").append(complexRootsMatcher.group(1)).append("\n");
        }

        return racines.length() > 0 ? racines.toString().trim() : "Aucune racine trouvée.";
    }


    /**
     * Envoie les données au Service Polynômes pour persistance.
     * @param polynome L'expression du polynôme.
     * @param racines Les racines calculées.
     */
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
            logger.error("Erreur lors de l'envoi au Service Polynômes", e);
        }
    }
}
