package com.emsi.servicefactorisation.controller;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.service.FactorisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factorisation")
public class FactorisationController {

    @Autowired
    private FactorisationService factorisationService;

    /**
     * Endpoint pour factoriser un polynôme.
     *
     * @param polynome L'expression du polynôme à factoriser.
     * @return La forme factorisée du polynôme.
     */
    @GetMapping("/factorize")
    public ResponseEntity<String> factorize(@RequestParam String polynome) {
        String factorizedExpression = factorisationService.factoriserPolynome(polynome);
        return ResponseEntity.ok(factorizedExpression);
    }
}