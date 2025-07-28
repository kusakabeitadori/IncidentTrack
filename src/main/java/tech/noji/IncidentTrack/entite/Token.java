package tech.noji.IncidentTrack.entite;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tokens")
public class Token {

    @Id
    @Column(columnDefinition = "text",unique=true)
    private String value;
    private boolean expired;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur utilisateur;
}

