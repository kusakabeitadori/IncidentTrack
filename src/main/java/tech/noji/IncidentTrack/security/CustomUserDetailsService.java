package tech.noji.IncidentTrack.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.noji.IncidentTrack.entite.Role;
import tech.noji.IncidentTrack.entite.Utilisateur;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(login)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        Role role = utilisateur.getRole();

        if (role == null) {
            throw new UsernameNotFoundException("Aucun rôle associé à l'utilisateur");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase());

        return new org.springframework.security.core.userdetails.User(
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                utilisateur.isActif(),
                true, true, true,
                List.of(authority)
        );
    }
}
