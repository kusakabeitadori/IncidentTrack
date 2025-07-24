package tech.noji.IncidentTrack.repository;

import tech.noji.IncidentTrack.entite.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findByName(String name);
}