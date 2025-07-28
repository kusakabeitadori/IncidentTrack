package tech.noji.IncidentTrack.entite;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @Column(unique = true)
    private String email;

    private String motDePasse;

    private boolean actif = true;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne()
    @JoinColumn(name = "service_id")
    private Departement service;


}




