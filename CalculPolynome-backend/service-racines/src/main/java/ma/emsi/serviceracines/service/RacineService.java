package ma.emsi.serviceracines.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RacineService {

    private static final Logger logger = LoggerFactory.getLogger(RacineService.class);

    @Value("${wolframalpha.api.key}")
    private String apiKey;

    @Value("${wolframalpha.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String calculerRacines(String polynome) {
        try {
            // Construire l'URL de requête
            String query = "solve " + polynome;
            String url = String.format("%s?input=%s&format=JSON&output=JSON&appid=%s",
                    apiUrl, query, apiKey);

            logger.info("Envoi de la requête vers Wolfram Alpha : {}", url);

            // Appel API
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Vérification du statut de la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Réponse API reçue avec succès.");
                return extraireRacines(response.getBody());
            } else {
                logger.error("Échec de l'appel API. Statut : {}", response.getStatusCode());
                return "Erreur lors de l'appel à l'API Wolfram Alpha.";
            }

        } catch (Exception e) {
            logger.error("Erreur lors de l'appel ou du traitement de Wolfram Alpha API", e);
            return "Une erreur est survenue lors du calcul des racines.";
        }
    }

    private String extraireRacines(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode pods = root.path("queryresult").path("pods");
            logger.info("Nombre de pods trouvés : {}", pods.size());

            // Parcourir les pods pour trouver celui avec "title": "Results"
            for (JsonNode pod : pods) {
                String title = pod.path("title").asText();
                logger.info("Pod trouvé avec titre : {}", title);

                if ("Results".equalsIgnoreCase(title)) {
                    StringBuilder racines = new StringBuilder();

                    // Parcourir les subpods pour récupérer les valeurs des racines
                    for (JsonNode subpod : pod.path("subpods")) {
                        String valeur = subpod.path("plaintext").asText().trim();
                        if (!valeur.isEmpty()) {
                            logger.info("Valeur trouvée : {}", valeur);
                            racines.append(valeur).append("\n");
                        }
                    }

                    if (racines.length() > 0) {
                        return racines.toString().trim();
                    }
                }
            }
            logger.warn("Aucun pod avec le titre 'Results' trouvé.");
            return "Aucune racine trouvée.";

        } catch (Exception e) {
            logger.error("Erreur lors de l'extraction des racines", e);
            return "Erreur lors de l'extraction des racines depuis la réponse API.";
        }
    }
}
