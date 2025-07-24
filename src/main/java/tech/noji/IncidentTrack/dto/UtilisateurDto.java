package tech.noji.IncidentTrack.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {
    private  Long id;
    private String nom;
    private String email;
    private Long serviceId;
    private  String serviceNom;
    private  Long roleId;
    private String roleNom;


}
