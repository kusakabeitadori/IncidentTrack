package tech.noji.IncidentTrack.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private UtilisateurDto utilisateurDto;
    private String accessToken;
    private String refreshToken;
}