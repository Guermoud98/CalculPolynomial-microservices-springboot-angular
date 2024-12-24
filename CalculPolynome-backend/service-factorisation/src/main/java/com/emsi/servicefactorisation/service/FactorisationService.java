package com.emsi.servicefactorisation.service;

import com.emsi.servicefactorisation.DTO.PolynomeDTO;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class FactorisationService {

    private static final Logger logger = LoggerFactory.getLogger(FactorisationService.class);

    @Value("${service.polynomes.url:http://localhost:8082}")
    private String polynomeServiceUrl;

    private final RestTemplate restTemplate;
    private final ExprEvaluator evaluator;

    public FactorisationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.evaluator = new ExprEvaluator(); // Initialisation de Matheclipse
    }

    public String factoriserPolynome(String polynome) {
        try {
            logger.info("Début de la factorisation pour le polynôme : {}", polynome);

            // Étape 1 : Factoriser le polynôme avec Symja
            String expression = "Factor[" + polynome + "]";
            IExpr result = evaluator.evaluate(expression);
            if (result == null) {
                logger.error("La factorisation a échoué, aucun résultat retourné.");
                throw new IllegalStateException("La factorisation a échoué.");
            }
            String factorized = result.toString();
            logger.info("Résultat de la factorisation : {}", factorized);

            // Étape 2 : Retourner uniquement la factorisation
            return factorized;
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de la factorisation :", e);
            throw new RuntimeException("Erreur inattendue : " + e.getMessage(), e);
        }
    }
}