package tech.noji.IncidentTrack.service;

import tech.noji.IncidentTrack.dto.ServiceDTO;
import java.util.List;

public interface ServiceEntityService {
    ServiceDTO createService(ServiceDTO serviceDTO);
    ServiceDTO getServiceById(Long id);
    List<ServiceDTO> getAllServices();
    ServiceDTO updateService(Long id, ServiceDTO serviceDTO);
    void deleteService(Long id);
}