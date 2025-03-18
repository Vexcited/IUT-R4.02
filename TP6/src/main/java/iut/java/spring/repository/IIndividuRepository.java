package iut.java.spring.repository;

import iut.java.spring.entity.Individu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIndividuRepository extends JpaRepository<Individu, Long> {
}
