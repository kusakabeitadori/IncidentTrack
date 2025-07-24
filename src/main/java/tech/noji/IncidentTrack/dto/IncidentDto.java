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
    private Long utilisateurAssigneId;
    private  String utilisateurAssigneNom;
    private  String utilisateurAssigneServiceNom;
    private Long createurIncidentId;
    private String createurIncidentNom;
    private LocalDateTime creationDate;

}
