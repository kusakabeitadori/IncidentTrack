package tech.noji.IncidentTrack.service;

import tech.noji.IncidentTrack.dto.DepartementDTO;
import java.util.List;

public interface DepartementService {
    DepartementDTO createService(DepartementDTO serviceDTO);
    DepartementDTO getServiceById(Long id);
    List<DepartementDTO> getAllServices();
    DepartementDTO updateService(Long id, DepartementDTO serviceDTO);
    void deleteService(Long id);
}