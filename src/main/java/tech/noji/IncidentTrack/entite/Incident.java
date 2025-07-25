//package tech.noji.IncidentTrack.entite;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "incidents")
////public class Incident {
////    public enum Status {
////        OUVERT,
////        ENCOURS,
////        RESOLU,
////        FERME
////    }
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////    private String titre;
////
////    @Column(columnDefinition = "TEXT")
////    private String description;
////
////    @Enumerated(EnumType.STRING)
////    private Status status = Status.OUVERT;
//////
//////    @ManyToOne(fetch = FetchType.LAZY)
//////    @JoinColumn(name = "createur_incident_id", nullable = false)
//////    private Utilisateur createurIncident;
//////
//////    @ManyToOne(fetch = FetchType.LAZY)
//////    @JoinColumn(name = "utilisateur_assigne_id")
//////    private Utilisateur utilisateurAssigne;
//////
//////    @Column(nullable = false)
//////    private LocalDateTime creationDate = LocalDateTime.now();
//////}
////


package tech.noji.IncidentTrack.entite;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incidents")
public class Incident {
    public enum Status {
        OUVERT,
        ENCOURS,
        RESOLU,
        FERME
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OUVERT;
//    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_incident_id", nullable = false)
    private Utilisateur createurIncident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_assigne_id")
    private Utilisateur utilisateurAssigne;

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();
    public static Incident createReference(Long id) {
        Incident incident = new Incident();
        incident.setId(id);
        return incident;
    }
}