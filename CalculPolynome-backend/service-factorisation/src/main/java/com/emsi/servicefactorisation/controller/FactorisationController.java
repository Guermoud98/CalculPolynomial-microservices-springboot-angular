package com.emsi.servicefactorisation.controller;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.service.FactorisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factorisation")
@RequiredArgsConstructor
public class FactorisationController {

    private final FactorisationService factorisationService;

    /**
     * Endpoint pour sauvegarder un polynôme dans la base de données.
     * @param expression L'expression du polynôme à sauvegarder.
     * @return Le polynôme sauvegardé.
     */
    @PostMapping("/polynomial")
    public ResponseEntity<Polynomial> savePolynomial(@RequestParam String expression) {
        Polynomial savedPolynomial = factorisationService.savePolynomial(expression);
        return ResponseEntity.ok(savedPolynomial);
    }

    /**
     * Endpoint pour factoriser un polynôme en utilisant l'API Wolfram Alpha.
     * @param id L'identifiant du polynôme à factoriser.
     * @return Le résultat de la factorisation.
     */
    @GetMapping("/factorize/{id}")
    public ResponseEntity<String> factorizePolynomial(@PathVariable Long id) {
        String factorizedResult = factorisationService.factorize(id);
        return ResponseEntity.ok(factorizedResult);
    }

    /**
     * Endpoint pour résoudre un polynôme et obtenir ses racines.
     * @param id L'identifiant du polynôme à résoudre.
     * @return Les racines du polynôme.
     */
    @GetMapping("/solve/{id}")
    public ResponseEntity<String> solvePolynomial(@PathVariable Long id) {
        String solvedResult = factorisationService.solve(id);
        return ResponseEntity.ok(solvedResult);
    }
}