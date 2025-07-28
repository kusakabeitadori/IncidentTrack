package tech.noji.IncidentTrack.service;

import tech.noji.IncidentTrack.dto.*;
import java.util.List;

public interface IncidentService {
    IncidentDto createAndAssignIncident(CreationIncidentRequest request);
    IncidentDto updateIncident(Long id, UpdateIncidentRequest request);
    IncidentDto reassignIncident(Long incidentId, Long newAgentId);
    void deleteIncident(Long id);
    IncidentDto changeStatus(Long incidentId, String newStatus,String details);
    List<IncidentDto> getAllIncidents();
    IncidentDto getIncidentById(Long incidentId);
    List<IncidentDto> getIncidentsByAgent(Long agentId);
    List<IncidentDto> getIncidentsByUserConnected();
}