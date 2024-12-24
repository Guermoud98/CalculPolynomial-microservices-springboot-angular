package com.emsi.servicefactorisation.controller;

import com.emsi.servicefactorisation.entity.Polynomial;
import com.emsi.servicefactorisation.service.FactorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factorisation")
public class FactorisationController {

    private FactorisationService factorisationService;

    public FactorisationController(FactorisationService factorisationService) {
        this.factorisationService=factorisationService;
    }

    @PostMapping("/factorize")
    public ResponseEntity<String> factorizeAndSend(@RequestParam String polynome) {
        try {
            // Appel au service pour factoriser
            String factorized = factorisationService.factoriserPolynome(polynome);

            // Retourner uniquement la valeur factorisée
            return ResponseEntity.ok(factorized);
        } catch (RuntimeException e) {
            // En cas d'erreur, retourner une réponse avec un statut HTTP approprié
            return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
        }
    }
}