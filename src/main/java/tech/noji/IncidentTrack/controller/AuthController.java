package tech.noji.IncidentTrack.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import tech.noji.IncidentTrack.dto.AuthRequest;
import tech.noji.IncidentTrack.dto.AuthResponse;
import tech.noji.IncidentTrack.service.TokenService;


import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = tokenService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur interne du serveur : "+ex.getMessage());
        }
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody String refreshToken, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(tokenService.refreshToken(refreshToken, request, response));
    }

    @PostMapping(value = "/revoke-token")
    public ResponseEntity<Void> revokeToken(@RequestBody String refreshToken) {
        tokenService.revokeTokens(refreshToken);
        return ResponseEntity.ok().build();
    }

}
