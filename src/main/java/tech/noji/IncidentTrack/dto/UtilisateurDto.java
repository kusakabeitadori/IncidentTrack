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
    private boolean actif;
    private Long roleId;
    private String roleName;
    private Long serviceId;
    private String serviceName;
}
