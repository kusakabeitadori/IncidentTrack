package tech.noji.IncidentTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noji.IncidentTrack.entite.Utilisateur;
import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByServiceId(Long serviceId);
    List<Utilisateur> findByRoleId(Long roleId);

    boolean existsByEmail(String mail);
//    List<Utilisateur> findByIncidentsAssignesIsNotEmpty();
}