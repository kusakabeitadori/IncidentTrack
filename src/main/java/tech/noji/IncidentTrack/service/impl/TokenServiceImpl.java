package tech.noji.IncidentTrack.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tech.noji.IncidentTrack.dto.AuthRequest;
import tech.noji.IncidentTrack.dto.AuthResponse;
import tech.noji.IncidentTrack.dto.UtilisateurDto;
import tech.noji.IncidentTrack.entite.Token;
import tech.noji.IncidentTrack.entite.Utilisateur;
import tech.noji.IncidentTrack.exception.InvalidTokenException;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.UtilisateurMapper;
import tech.noji.IncidentTrack.repository.TokenRepository;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;
import tech.noji.IncidentTrack.security.JwtUtil;
import tech.noji.IncidentTrack.service.TokenService;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT_LOGGER");

    private final TokenRepository tokenRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final UtilisateurMapper utilisateurMapper;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticateCredentials(request.getEmail(), request.getPassword());

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur notfound"));

        utilisateurRepository.save(utilisateur);
        return generateAuthResponse(utilisateur);
    }



    @Override
    public AuthResponse refreshToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (refreshToken == null || refreshToken.isEmpty()) {
            auditLogger.error("Refresh token manquant ou vide");
            throw new InvalidTokenException("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        if (username == null) {
            auditLogger.error("le username du refresh token manquant ou vide");
            throw new InvalidTokenException("le username du refresh token manquant ou vide");
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found"));

        boolean isTokenValid = tokenRepository.findByValue(refreshToken)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        if (!jwtUtil.isTokenValid(refreshToken, username) || !isTokenValid) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        return generateAuthResponse(utilisateur);
    }

    @Override
    public void revokeTokens(String refreshToken) {
        Optional<Token> tokens = tokenRepository.findByValue(refreshToken);
        if (tokens.isPresent()) {
            tokens.get().setExpired(true);
            tokens.get().setRevoked(true);
            tokenRepository.save(tokens.get());
        }
    }


    private void saveUserToken(Utilisateur utilisateur, String jwtToken) {
        Token token = Token.builder()
                .utilisateur(utilisateur)
                .value(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllTokensWithUser(Utilisateur user) {
        List<Token> validUserTokens = tokenRepository.findByUtilisateurAndRevokedFalse(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void authenticateCredentials(String login, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
    }

    private AuthResponse generateAuthResponse(Utilisateur utilisateur) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(utilisateur.getEmail());

        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        revokeAllTokensWithUser(utilisateur);
        saveUserToken(utilisateur, refreshToken);
        return AuthResponse.builder()
                .utilisateurDto(buildUtilisateurDto(utilisateur))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    private UtilisateurDto buildUtilisateurDto(Utilisateur utilisateur) {
        return utilisateurMapper.toDto(utilisateur);
    }
}
