package ma.emsi.servicepolynome;

import ma.emsi.servicepolynome.entity.Polynome;
import ma.emsi.servicepolynome.repository.PolynomeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServicePolynomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePolynomeApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PolynomeRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Polynome p1 = new Polynome();
                p1.setExpression("x^3 - 6*x^2 + 11*x - 6");
                p1.setDescription("Exemple de polynôme de degré 3");

                Polynome p2 = new Polynome();
                p2.setExpression("x^2 + 5*x + 6");
                p2.setDescription("Exemple de polynôme quadratique");

                repository.save(p1);
                repository.save(p2);

                System.out.println("Base de données initialisée avec 2 polynômes.");
            } else {
                System.out.println("La base de données contient déjà des données.");
            }
        };
    }
}
