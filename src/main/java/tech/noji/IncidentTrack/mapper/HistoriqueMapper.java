package tech.noji.IncidentTrack.mapper;

import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.HistoriqueIncidentDto;
import tech.noji.IncidentTrack.entite.HistoriqueIncident;

@Component
public class HistoriqueMapper {
    public HistoriqueIncidentDto toDto(HistoriqueIncident historique) {
        return new HistoriqueIncidentDto(
                historique.getId(),
                historique.getIncident().getId(),
                historique.getIncident().getTitre(),
                historique.getUtilisateur().getId(),
                historique.getUtilisateur().getNom(),
                historique.getAction(),
                historique.getDetails(),
                historique.getDateAction()
        );
    }
}