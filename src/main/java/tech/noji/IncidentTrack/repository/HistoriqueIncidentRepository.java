package tech.noji.IncidentTrack.repository;

import tech.noji.IncidentTrack.entite.HistoriqueIncident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoriqueIncidentRepository extends JpaRepository<HistoriqueIncident, Long> {
    List<HistoriqueIncident> findByIncidentIdOrderByDateActionDesc(Long incidentId);
    List<HistoriqueIncident> findAllByIncidentId(Long incidentId);
    Page<HistoriqueIncident> findAll(Pageable pageable);
}