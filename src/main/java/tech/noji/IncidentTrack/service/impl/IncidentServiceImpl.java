
package tech.noji.IncidentTrack.service.impl;
import tech.noji.IncidentTrack.dto.*;
import tech.noji.IncidentTrack.entite.Incident;
import tech.noji.IncidentTrack.entite.Utilisateur;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.IncidentMapper;
import tech.noji.IncidentTrack.repository.IncidentRepository;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.noji.IncidentTrack.service.IncidentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final IncidentMapper incidentMapper;

    public IncidentServiceImpl(IncidentRepository incidentRepository,
                               UtilisateurRepository utilisateurRepository,
                               IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.incidentMapper = incidentMapper;
    }

    @Override
    public IncidentDto createAndAssignIncident(CreationIncidentRequest request) {
        Incident incident = incidentMapper.toEntity(request);
        Incident savedIncident = incidentRepository.save(incident);
        return incidentMapper.toDto(savedIncident);
    }

    @Override
    public IncidentDto updateIncident(Long id, UpdateIncidentRequest request) {
        return null;
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
    public IncidentDto changeStatus(Long incidentId, String newStatus) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident non trouvé"));

        try {
            Incident.Status status = Incident.Status.valueOf(newStatus.toUpperCase());
            incident.setStatus(status);
            Incident updatedIncident = incidentRepository.save(incident);
            return incidentMapper.toDto(updatedIncident);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide: " + newStatus);
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