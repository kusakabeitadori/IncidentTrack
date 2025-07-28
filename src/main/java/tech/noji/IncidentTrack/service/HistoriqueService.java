package tech.noji.IncidentTrack.service;

import org.springframework.data.domain.Page;
import tech.noji.IncidentTrack.dto.HistoriqueIncidentDto;

import java.util.List;

public interface HistoriqueService {
    void enregistrerAction(Long incidentId, Long utilisateurId, String action, String details);
    void save(Long incidentId, String action, String details);
    List<HistoriqueIncidentDto> getHistoriqueIncident(Long incidentId);
    HistoriqueIncidentDto getHistoriqueEntry(Long id);
    void deleteHistoriqueEntry(Long id);
}