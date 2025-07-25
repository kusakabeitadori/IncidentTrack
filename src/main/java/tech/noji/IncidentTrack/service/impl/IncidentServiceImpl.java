
package tech.noji.IncidentTrack.service.impl;
import tech.noji.IncidentTrack.dto.*;
import tech.noji.IncidentTrack.entite.HistoriqueIncident;
import tech.noji.IncidentTrack.entite.Incident;
import tech.noji.IncidentTrack.entite.Utilisateur;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.IncidentMapper;
import tech.noji.IncidentTrack.repository.IncidentRepository;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.noji.IncidentTrack.service.ActionHistorique;
import tech.noji.IncidentTrack.service.IncidentService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final IncidentMapper incidentMapper;
    private final HistoriqueIncident historiqueIncident;

    public IncidentServiceImpl(IncidentRepository incidentRepository,
                               UtilisateurRepository utilisateurRepository,
                               IncidentMapper incidentMapper, HistoriqueIncident historiqueIncident) {
        this.incidentRepository = incidentRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.incidentMapper = incidentMapper;
        this.historiqueIncident = historiqueIncident;
    }

    @Override
    public IncidentDto createAndAssignIncident(CreationIncidentRequest request) {
        Incident incident = incidentMapper.toEntity(request);
        Incident savedIncident = incidentRepository.save(incident);
//        historiqueService.enregistrerAction(
//                savedIncident.getId(),
//                request.getCreateurIncidentId(),
//                HistoriqueIncident.Action.CREATION,
//                "Création de l'incident"
//        );
        return incidentMapper.toDto(savedIncident);
    }

//    @Override
//    public IncidentDto updateIncident(Long id, UpdateIncidentRequest request) {
//        return null;
//    }

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

        if (request.getStatus() != null) {
            incident.setStatus(request.getStatus());
        }

        if (request.getUtilisateurAssigneId() != null) {
            Utilisateur nouvelAssigné = utilisateurRepository.findById(request.getUtilisateurAssigneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
            incident.setUtilisateurAssigne(nouvelAssigné);
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

//    @Override
//    public IncidentDto changeStatus(Long incidentId, String newStatus) {
//        Incident incident = incidentRepository.findById(incidentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));
//
//        try {
//            Incident.Status status = Incident.Status.valueOf(newStatus.toUpperCase());
//            incident.setStatus(status);
//            Incident updatedIncident = incidentRepository.save(incident);
//            return incidentMapper.toDto(updatedIncident);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Statut invalide: " + newStatus);
//        }
//    }
public IncidentDto changeStatus(Long incidentId, String newStatus) {
    Incident incident = incidentRepository.findById(incidentId)
            .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));

    try {
        Incident.Status status = Incident.Status.valueOf(newStatus.toUpperCase());
        incident.setStatus(status);
        return incidentMapper.toDto(incidentRepository.save(incident));
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
                "Statut invalide. Les statuts valides sont: " +
                        Arrays.toString(Incident.Status.values())
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
}