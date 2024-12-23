package ma.emsi.servicepolynome.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Polynome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String expression; // Exemple : x^3 - 6x^2 + 11x - 6

    public String getRacines() {
        return racines;
    }

    public void setRacines(String racines) {
        this.racines = racines;
    }

    private String description; // Une description optionnelle.

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT") // Si vous utilisez MySQL
    private String racines;

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
}
