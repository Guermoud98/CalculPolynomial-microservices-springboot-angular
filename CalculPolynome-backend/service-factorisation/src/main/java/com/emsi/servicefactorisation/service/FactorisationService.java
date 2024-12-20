package com.emsi.servicefactorisation.service;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.repository.PolynomialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor // Génère le constructeur avec les arguments nécessaires
public class FactorisationService {

    @Value("${wolframalpha.api.key}")
    private String apiKey;

    @Value("${wolframalpha.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final PolynomialRepository polynomialRepository;

    public String factorize(Long id) {
        Optional<Polynomial> polynomial = polynomialRepository.findById(id);
        if (polynomial.isEmpty()) {
            throw new RuntimeException("Polynomial not found with id: " + id);
        }

        String query = "factor " + polynomial.get().getExpression();
        String url = String.format("%s?input=%s&format=plaintext&appid=%s", apiUrl, query, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String solve(Long id) {
        Optional<Polynomial> polynomial = polynomialRepository.findById(id);
        if (polynomial.isEmpty()) {
            throw new RuntimeException("Polynomial not found with id: " + id);
        }

        String query = "solve " + polynomial.get().getExpression();
        String url = String.format("%s?input=%s&format=plaintext&appid=%s", apiUrl, query, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public Polynomial savePolynomial(String expression) {
        Polynomial polynomial = new Polynomial(expression);
        return polynomialRepository.save(polynomial);
    }
}
