package tech.noji.IncidentTrack.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.noji.IncidentTrack.dto.AuthRequest;
import tech.noji.IncidentTrack.dto.AuthResponse;

import java.io.IOException;

public interface TokenService {

    AuthResponse authenticate(AuthRequest request);
    AuthResponse refreshToken(String refreshToken,
                                        HttpServletRequest request,
                                        HttpServletResponse response
    ) throws IOException;
    void  revokeTokens(String refreshToken);
}
