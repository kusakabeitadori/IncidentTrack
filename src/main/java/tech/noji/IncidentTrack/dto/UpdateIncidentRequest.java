package tech.noji.IncidentTrack.dto;

import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.noji.IncidentTrack.entite.Incident;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIncidentRequest {
        private String titre;
        private String description;
        private Long utilisateurAssigneId;
       private Incident.Status status;

}