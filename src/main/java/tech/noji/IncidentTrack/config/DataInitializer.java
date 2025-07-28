package tech.noji.IncidentTrack.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.entite.Departement;
import tech.noji.IncidentTrack.entite.Role;
import tech.noji.IncidentTrack.entite.Utilisateur;
import tech.noji.IncidentTrack.repository.DepartementRepository;
import tech.noji.IncidentTrack.repository.RoleRepository;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private RoleRepository roleRepository;

    private UtilisateurRepository utilisateurRepository;

    private DepartementRepository departementRepository;
    private final PasswordEncoder passwordEncoder;

    public void run(String... args) throws Exception {
        this.initData();
    }

    public void initData() {
        // --- Créer les rôles si pas encore existants ---
        Role adminRole = createRoleIfNotExists("ADMIN");
        Role receptionisteRole = createRoleIfNotExists("RECEPTIONISTE");

        // --- Créer un département ---
        Departement dep = new Departement();
        dep.setName("Informatique");
        Departement departement = departementRepository.save(dep);


        // --- Créer un utilisateur admin ---
        if (!utilisateurRepository.existsByEmail("admin@example.com")) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Administrateur");
            admin.setEmail("admin@example.com");
            admin.setMotDePasse(passwordEncoder
                    .encode("admin123"));
            admin.setActif(true);
            admin.setRole(adminRole);
            admin.setService(departement);
            utilisateurRepository.save(admin);
        }
        if (!utilisateurRepository.existsByEmail("receptioniste@example.com")) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("RECEPTIONISTE");
            admin.setEmail("receptioniste@example.com");
            admin.setMotDePasse(passwordEncoder
                    .encode("receptioniste123"));
            admin.setActif(true);
            admin.setRole(receptionisteRole);
            admin.setService(departement);
            utilisateurRepository.save(admin);
        }

    }

    private Role createRoleIfNotExists(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    return roleRepository.save(role);
                });
    }
}

