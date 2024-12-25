package ma.emsi.servicepolynome.service;


import ma.emsi.servicepolynome.entity.Polynome;
import ma.emsi.servicepolynome.repository.PolynomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolynomeService {

    @Autowired
    private PolynomeRepository repository;

    /**
     * Sauvegarder un nouveau polynôme.
     */
    public Polynome savePolynome(Polynome polynome) {
        return repository.save(polynome);
    }

    /**
     * Rechercher un polynôme par son expression.
     */
    public Polynome findByExpression(String expression) {
        return repository.findByExpression(expression);
    }

    /**
     * Mettre à jour un polynôme existant.
     */
    public Polynome updatePolynome(Polynome polynome) {
        // Vérifier si le polynôme existe déjà dans la base de données
        Polynome existingPolynome = repository.findById(polynome.getId())
                .orElseThrow(() -> new RuntimeException("Polynôme introuvable avec l'ID : " + polynome.getId()));

        // Mettre à jour les champs nécessaires
        if (polynome.getExpression() != null) {
            existingPolynome.setExpression(polynome.getExpression());
        }
        if (polynome.getRacines() != null) {
            existingPolynome.setRacines(polynome.getRacines());
        }
        if (polynome.getDescription() != null) {
            existingPolynome.setDescription(polynome.getDescription());
        }

        // Sauvegarder les modifications
        return repository.save(existingPolynome);
    }

    /**
     * Récupérer tous les polynômes.
     */
    public List<Polynome> getAllPolynomes() {
        return repository.findAll();
    }

    /**
     * Récupérer un polynôme par son ID.
     */
    public Polynome getPolynomeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Polynôme introuvable avec l'ID : " + id));
    }
}
