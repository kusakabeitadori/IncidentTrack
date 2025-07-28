package tech.noji.IncidentTrack.service;

import tech.noji.IncidentTrack.dto.*;
import java.util.List;

public interface IncidentService {
    IncidentDto createAndAssignIncident(CreationIncidentRequest request);
    IncidentDto reassignIncident(Long incidentId, Long newAgentId);
    void deleteIncident(Long id);
    IncidentDto changeStatus(Long incidentId, String newStatus);
    List<IncidentDto> getAllIncidents();
    IncidentDto getIncidentById(Long incidentId);
    List<IncidentDto> getIncidentsByAgent(Long agentId);
}