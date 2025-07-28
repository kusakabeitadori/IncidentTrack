package tech.noji.IncidentTrack.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.noji.IncidentTrack.entite.Incident;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDto {

    private Long id;
    private  String titre;
    private  String description;
    private  String status;
    private LocalDateTime creationDate;
    private Long createurIncidentId;
    private String createurIncidentNom;
    private String createurIncidentEmail;
    private Long createurIncidentServiceId;
    private String createurIncidentServiceName;
    private Long utilisateurAssigneId;
    private String utilisateurAssigneNom;
    private Long utilisateurAssigneServiceId;
    private String utilisateurAssigneServiceName;
}
