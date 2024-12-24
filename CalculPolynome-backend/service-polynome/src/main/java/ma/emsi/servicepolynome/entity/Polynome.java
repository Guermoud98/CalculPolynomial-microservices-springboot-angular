package ma.emsi.servicepolynome.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Polynome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String expression; // Exemple : x^3 - 6x^2 + 11x - 6

    private String description; // Une description optionnelle.

    private LocalDateTime createdAt;

    @Column
    private String factorizedValue; // Pour stocker la valeur factorisée

    public Polynome() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getFactorizedValue() {
        return factorizedValue;
    }

    public void setFactorizedValue(String factorizedValue) {
        this.factorizedValue = factorizedValue;
    }
}