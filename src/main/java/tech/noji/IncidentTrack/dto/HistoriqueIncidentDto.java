package tech.noji.IncidentTrack.dto;

import lombok.Data;
import tech.noji.IncidentTrack.entite.HistoriqueIncident;
import tech.noji.IncidentTrack.entite.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link HistoriqueIncident}
 */
@Data
public class HistoriqueIncidentDto implements Serializable {
    Long id;
    Long incidentId;
    String incidentTitre;
    String incidentDescription;
    Status incidentStatus;
    Long utilisateurId;
    String utilisateurNom;
    String utilisateurEmail;
    String action;
    String details;
    LocalDateTime dateAction;
}