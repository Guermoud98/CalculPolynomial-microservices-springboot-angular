package ma.emsi.serviceracines.controller;
import jakarta.validation.Valid;
import ma.emsi.serviceracines.dto.PolynomeRequest;
import ma.emsi.serviceracines.service.RacineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/racines")
public class RacineController {

    @Autowired
    private RacineService racineService;

    @PostMapping("/calculer")
    public ResponseEntity<String> calculerRacines(@Valid @RequestBody PolynomeRequest request) {
        String result = racineService.calculerRacines(request.getPolynome());
        return ResponseEntity.ok(result);
    }
}
