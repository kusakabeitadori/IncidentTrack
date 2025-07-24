package tech.noji.IncidentTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noji.IncidentTrack.entite.Incident;
import tech.noji.IncidentTrack.entite.Utilisateur;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByUtilisateurAssigneId(Long agentId);
    List<Incident> findByUtilisateurAssigne(Utilisateur utilisateur);
}