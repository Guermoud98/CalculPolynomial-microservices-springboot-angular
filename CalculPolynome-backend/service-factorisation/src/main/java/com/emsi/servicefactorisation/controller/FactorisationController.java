package com.emsi.servicefactorisation.controller;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.service.FactorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factorisation")
public class FactorisationController {

    @Autowired
    private FactorisationService factorisationService;
    
    @PostMapping("/polynomial")
    public ResponseEntity<Polynomial> savePolynomial(@RequestParam String polynome) {
        Polynomial savedPolynomial = factorisationService.savePolynomial(polynome);
        return ResponseEntity.ok(savedPolynomial);
    }
    @GetMapping("/factorize")
    public ResponseEntity<String> factorize(@RequestParam String polynome) {
        String factorizedExpression = factorisationService.factoriserPolynome(polynome);
        return ResponseEntity.ok(factorizedExpression);
    }
}