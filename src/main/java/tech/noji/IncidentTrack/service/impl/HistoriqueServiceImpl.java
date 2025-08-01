package tech.noji.IncidentTrack.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.noji.IncidentTrack.dto.HistoriqueIncidentDto;
import tech.noji.IncidentTrack.entite.HistoriqueIncident;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.HistoriqueMapper;
import tech.noji.IncidentTrack.mapper.UtilisateurMapper;
import tech.noji.IncidentTrack.repository.HistoriqueIncidentRepository;
import tech.noji.IncidentTrack.repository.IncidentRepository;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;
import tech.noji.IncidentTrack.service.HistoriqueService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueServiceImpl implements HistoriqueService {
    private final HistoriqueIncidentRepository historiqueRepository;
    private final IncidentRepository incidentRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final HistoriqueMapper historiqueMapper;
    private final AuthService authService;

    @Override
    @Transactional
    public void enregistrerAction(Long incidentId, Long utilisateurId, String action, String details) {
        HistoriqueIncident historique = new HistoriqueIncident();
        historique.setIncident(incidentRepository.getReferenceById(incidentId));
        historique.setUtilisateur(utilisateurRepository.getReferenceById(utilisateurId));
        historique.setAction(action);
        historique.setDetails(details);
        historiqueRepository.save(historique);
    }

    @Override
    public void save(Long incidentId, String action, String details) {
            HistoriqueIncident historique = new HistoriqueIncident();
            historique.setIncident(incidentRepository.getReferenceById(incidentId));
            historique.setAction(action);
            historique.setUtilisateur(authService.getCurrentUser());
            historique.setDetails(details);
            historiqueRepository.save(historique);
    }

    @Override
    public List<HistoriqueIncidentDto> getHistoriqueIncident(Long incidentId) {
        return historiqueRepository.findByIncidentIdOrderByDateActionDesc(incidentId)
                .stream()
                .map(historiqueMapper::toDto)
                .toList();
    }


    @Override
    public HistoriqueIncidentDto getHistoriqueEntry(Long id) {
        return historiqueRepository.findById(id)
                .map(historiqueMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Entrée historique non trouvée"));
    }

    @Override
    @Transactional
    public void deleteHistoriqueEntry(Long id) {
        if (!historiqueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entrée historique non trouvée");
        }
        historiqueRepository.deleteById(id);
    }
}