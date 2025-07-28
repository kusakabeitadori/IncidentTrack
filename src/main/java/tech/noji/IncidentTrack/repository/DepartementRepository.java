package tech.noji.IncidentTrack.repository;

import tech.noji.IncidentTrack.entite.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
    Optional<Departement> findByName(String name);
}