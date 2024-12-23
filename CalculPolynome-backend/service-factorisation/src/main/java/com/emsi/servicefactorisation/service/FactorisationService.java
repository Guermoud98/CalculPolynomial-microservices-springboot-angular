package com.emsi.servicefactorisation.service;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.repository.PolynomialRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class FactorisationService {
    @Autowired
    private PolynomialRepository polynomialRepository;

    private final ExprEvaluator evaluator;

    public FactorisationService() {
        this.evaluator = new ExprEvaluator(); // Initialisation de Matheclipse
    }

    public Polynomial savePolynomial(String expression) {
        Polynomial polynomial = new Polynomial();
        polynomial.setExpression(expression);
        return polynomialRepository.save(polynomial);
    }

    public String factoriserPolynome(String polynome) {
        try {
            String expression = "Factor[" + polynome + "]";
            IExpr result = evaluator.evaluate(expression); // Évalue l'expression avec Matheclipse
            return result.toString(); // Renvoie le résultat factorisé
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la factorisation du polynôme", e);
        }
    }
}
