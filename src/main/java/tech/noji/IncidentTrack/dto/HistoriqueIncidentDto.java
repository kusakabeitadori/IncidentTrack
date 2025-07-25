package tech.noji.IncidentTrack.dto;

import java.time.LocalDateTime;

public record HistoriqueIncidentDto(
        Long id,
        Long incidentId,
        String incidentTitre,
        Long utilisateurId,
        String nomUtilisateur,
        String action,
        String details,
        LocalDateTime dateAction
) {}