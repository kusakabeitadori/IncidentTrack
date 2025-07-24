package tech.noji.IncidentTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreationUtilisateurRequestDto {
    private String nom;
    private String email;
    private Long roleId;
    private Long serviceId;
    private String password;




}
