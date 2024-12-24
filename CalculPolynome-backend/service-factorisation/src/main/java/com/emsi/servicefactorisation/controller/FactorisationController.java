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

    @PostMapping("/factorize")
    public ResponseEntity<String> factorizeAndSend(@RequestParam String polynome) {
        try {
            // Appel au service pour factoriser et envoyer les données
            String response = factorisationService.factoriserPolynome(polynome);

            // Retourner la réponse du service-polynome
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // En cas d'erreur, retourner une réponse avec un statut HTTP approprié
            return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
        }
    }
}