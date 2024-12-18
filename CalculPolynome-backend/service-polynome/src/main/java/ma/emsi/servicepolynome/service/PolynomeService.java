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

    public Polynome savePolynome(Polynome polynome) {
        return repository.save(polynome);
    }

    public List<Polynome> getAllPolynomes() {
        return repository.findAll();
    }

    public Polynome getPolynomeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Polynôme non trouvé !"));
    }
}
