package tech.noji.IncidentTrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.noji.IncidentTrack.entite.Token;
import tech.noji.IncidentTrack.entite.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findByUtilisateurAndExpiredFalseAndRevokedFalse(Utilisateur utilisateur);
    List<Token> findByUtilisateurAndRevokedFalse(Utilisateur utilisateur);
    Optional<Token> findByValue(String value);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.utilisateur.email = :email")
    void deleteAllByUserLogin(@Param("email") String email);
}