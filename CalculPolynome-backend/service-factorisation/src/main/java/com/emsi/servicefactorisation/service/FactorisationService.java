package com.emsi.servicefactorisation.service;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.repository.PolynomialRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class FactorisationService {

    @Value("${wolframalpha.api.key}")
    private String apiKey;

    @Value("${wolframalpha.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public FactorisationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String factoriserPolynome(String polynome) {
        try {
            String query = "factor " + polynome;
            String url = String.format("%s?input=%s&format=JSON&output=JSON&appid=%s",
                    apiUrl, query, apiKey);

            // Appel à l'API Wolfram Alpha
            String response = restTemplate.getForObject(url, String.class);

            // Extraire le résultat de factorisation
            return extractFactorizedExpression(response);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la factorisation du polynôme", e);
        }
    }

    private String extractFactorizedExpression(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode pods = root.path("queryresult").path("pods");
            for (JsonNode pod : pods) {
                if ("Factored form".equalsIgnoreCase(pod.path("title").asText())) {
                    return pod.path("subpods").get(0).path("plaintext").asText();
                }
            }
            return "Aucune factorisation trouvée.";
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'extraction de la factorisation", e);
        }
    }
}
