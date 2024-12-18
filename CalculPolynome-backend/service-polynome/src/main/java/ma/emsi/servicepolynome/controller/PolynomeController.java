package ma.emsi.servicepolynome.controller;

import ma.emsi.servicepolynome.entity.Polynome;
import ma.emsi.servicepolynome.service.PolynomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polynomes")
public class PolynomeController {

    @Autowired
    private PolynomeService service;

    @PostMapping("/save")
    public ResponseEntity<Polynome> savePolynome(@RequestBody Polynome polynome) {
        Polynome savedPolynome = service.savePolynome(polynome);
        return ResponseEntity.ok(savedPolynome);
    }

    @GetMapping
    public ResponseEntity<List<Polynome>> getAllPolynomes() {
        return ResponseEntity.ok(service.getAllPolynomes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Polynome> getPolynomeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPolynomeById(id));
    }
}
