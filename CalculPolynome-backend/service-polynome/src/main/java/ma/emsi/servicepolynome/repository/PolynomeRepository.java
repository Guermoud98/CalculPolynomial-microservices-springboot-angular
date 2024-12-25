package ma.emsi.servicepolynome.repository;


import ma.emsi.servicepolynome.entity.Polynome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PolynomeRepository extends JpaRepository<Polynome, Long> {
    Polynome findByExpression(String expression);

}

