package ma.emsi.serviceracines.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour transférer les données au Service Polynômes.
 */
public class PolynomeDTO {

    @NotBlank(message = "L'expression du polynôme est obligatoire.")
    private String expression;

    private String racines; // Les racines calculées pour le polynôme.

    // Constructeurs
    public PolynomeDTO() {
    }

    public PolynomeDTO(String expression, String racines) {
        this.expression = expression;
        this.racines = racines;
    }

    // Getters et Setters
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getRacines() {
        return racines;
    }

    public void setRacines(String racines) {
        this.racines = racines;
    }
}
