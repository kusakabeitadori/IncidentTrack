
package tech.noji.IncidentTrack.service.impl;
import tech.noji.IncidentTrack.dto.*;
import tech.noji.IncidentTrack.entite.Incident;
import tech.noji.IncidentTrack.entite.Status;
import tech.noji.IncidentTrack.entite.Utilisateur;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.IncidentMapper;
import tech.noji.IncidentTrack.repository.IncidentRepository;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.noji.IncidentTrack.service.HistoriqueService;
import tech.noji.IncidentTrack.service.IncidentService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final IncidentMapper incidentMapper;
    private final HistoriqueService historiqueService;
    private final AuthService authService;

    public IncidentServiceImpl(IncidentRepository incidentRepository,
                               UtilisateurRepository utilisateurRepository,
                               IncidentMapper incidentMapper, HistoriqueService historiqueService, AuthService authService) {
        this.incidentRepository = incidentRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.incidentMapper = incidentMapper;
        this.historiqueService = historiqueService;
        this.authService = authService;
    }

    @Override
    public IncidentDto createAndAssignIncident(CreationIncidentRequest request) {
        Utilisateur utilisateurAssigne = utilisateurRepository.findById(request.getUtilisateurAssigneId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        Incident incident = new Incident();
        incident.setTitre(request.getTitre());
        incident.setDescription(request.getDescription());
        incident.setCreateurIncident(authService.getCurrentUser());
        incident.setUtilisateurAssigne(utilisateurAssigne);
        incident.setStatus(Status.OUVERT);
        Incident savedIncident = incidentRepository.save(incident);
        historiqueService.save(savedIncident.getId(), String.valueOf(Status.OUVERT),"Ajout d'un ticket");
        return incidentMapper.toDto(savedIncident);
    }


    @Transactional
    public IncidentDto updateIncident(Long id, UpdateIncidentRequest request) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));

        if (request.getTitre() != null) {
            incident.setTitre(request.getTitre());
        }

        if (request.getDescription() != null) {
            incident.setDescription(request.getDescription());
        }

        if (request.getUtilisateurAssigneId() != null) {
            Utilisateur nouvelAssigne = utilisateurRepository.findById(request.getUtilisateurAssigneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
            incident.setUtilisateurAssigne(nouvelAssigne);
        }
        Incident updatedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(updatedIncident);
    }

    @Override
    public IncidentDto reassignIncident(Long incidentId, Long newAgentId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));

        Utilisateur newAgent = utilisateurRepository.findById(newAgentId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        incident.setUtilisateurAssigne(newAgent);
        Incident updatedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(updatedIncident);
    }

    @Override
    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }

@Override
public IncidentDto changeStatus(Long incidentId, String newStatus,String details) {
    Incident incident = incidentRepository.findById(incidentId)
            .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));

    try {
        Status status = Status.valueOf(newStatus.toUpperCase());
        incident.setStatus(status);
        Incident incidetSaved = incidentRepository.save(incident);
        historiqueService.save(incidetSaved.getId(),newStatus,details);
        return incidentMapper.toDto(incidentRepository.save(incident));
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
                "Statut invalide. Les statuts valides sont: " +
                        Arrays.toString(Status.values())
        );
    }
}

    @Override
    public List<IncidentDto> getAllIncidents() {
        return incidentRepository.findAll().stream()
                .map(incidentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public IncidentDto getIncidentById(Long incidentId) {
        return incidentRepository.findById(incidentId)
                .map(incidentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));
    }

    @Override
    public List<IncidentDto> getIncidentsByAgent(Long agentId) {
        Utilisateur agent = utilisateurRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        return incidentRepository.findByUtilisateurAssigne(agent).stream()
                .map(incidentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidentDto> getIncidentsByUserConnected() {
        return incidentRepository.findByUtilisateurAssigneId(authService.getCurrentUser().getId()).stream()
                .map(incidentMapper::toDto)
                .collect(Collectors.toList());
    }
}