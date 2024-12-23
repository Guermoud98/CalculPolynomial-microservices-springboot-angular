package ma.emsi.serviceracines.controller;

import jakarta.validation.Valid;
import ma.emsi.serviceracines.dto.PolynomeDTO;
import ma.emsi.serviceracines.service.RacineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour gérer les requêtes liées aux calculs des racines.
 */
@RestController
@RequestMapping("/racines")
public class RacineController {

    @Autowired
    private RacineService racineService;

    /**
     * Point d'entrée POST pour calculer les racines d'un polynôme.
     * @param polynomeDTO Contient l'expression du polynôme.
     * @return Les racines calculées ou un message d'erreur.
     */
    @PostMapping("/calculer")
    public ResponseEntity<String> calculerRacines(@Valid @RequestBody PolynomeDTO polynomeDTO) {
        String result = racineService.calculerRacines(polynomeDTO.getExpression());
        return ResponseEntity.ok(result);
    }
}
