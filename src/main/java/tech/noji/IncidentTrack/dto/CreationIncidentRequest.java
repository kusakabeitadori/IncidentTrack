package tech.noji.IncidentTrack.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreationIncidentRequest {
    private String titre;
    private String description;
    private Long utilisateurAssigneId;
    private Long createurIncidentId;


}
