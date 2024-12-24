package com.emsi.servicefactorisation.service;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.repository.PolynomialRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

@Service
public class FactorisationService {

    private final RestTemplate restTemplate;
    private final ExprEvaluator evaluator;

    @Value("${service.polynomes.url}")
    private String polynomeServiceUrl; // URL du service-polynome

    @PostConstruct
    public void validateConfig() {
        if (polynomeServiceUrl == null || polynomeServiceUrl.isEmpty()) {
            throw new IllegalStateException("L'URL du service-polynome n'est pas configurée !");
        }
    }
    public FactorisationService(RestTemplate restTemplate) {
        this.evaluator = new ExprEvaluator(); // Initialisation de Matheclipse
        this.restTemplate = restTemplate;
    }

    public String factoriserPolynome(String polynome) {
        try {
            // Étape 1 : Factoriser le polynôme
            String expression = "Factor[" + polynome + "]";
            IExpr result = evaluator.evaluate(expression); // Évalue l'expression
            String factorized = result.toString(); // Résultat factorisé

            // Étape 2 : Créer l'objet Polynome
            HashMap<String, String> data = new HashMap<>();
            data.put("expression", polynome);
            data.put("factorizedValue", factorized);

            // Étape 3 : Envoyer les données au service-polynome
            String response = restTemplate.postForObject(polynomeServiceUrl + "/save", data, String.class);

            return response; // Retourner la réponse du service-polynome
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la factorisation ou de l'envoi du polynôme", e);
        }
    }
    }

