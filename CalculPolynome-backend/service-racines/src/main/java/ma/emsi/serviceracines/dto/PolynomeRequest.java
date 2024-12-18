package ma.emsi.serviceracines.dto;

import jakarta.validation.constraints.NotBlank;

public class PolynomeRequest {

    @NotBlank(message = "L'expression du polynôme est obligatoire.")
    private String polynome;

    // Getters et Setters
    public String getPolynome() {
        return polynome;
    }

    public void setPolynome(String polynome) {
        this.polynome = polynome;
    }
}