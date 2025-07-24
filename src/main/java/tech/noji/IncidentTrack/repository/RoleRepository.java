package tech.noji.IncidentTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noji.IncidentTrack.entite.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String name);

}