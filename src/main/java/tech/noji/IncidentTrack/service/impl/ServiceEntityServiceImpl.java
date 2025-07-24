package tech.noji.IncidentTrack.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.noji.IncidentTrack.dto.ServiceDTO;
import tech.noji.IncidentTrack.entite.ServiceEntity;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.ServiceMapper;
import tech.noji.IncidentTrack.repository.ServiceRepository;
import tech.noji.IncidentTrack.service.ServiceEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceEntityServiceImpl(ServiceRepository serviceRepository,
                                    ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        ServiceEntity serviceEntity = serviceMapper.toEntity(serviceDTO);
        ServiceEntity savedEntity = serviceRepository.save(serviceEntity);
        return serviceMapper.toDto(savedEntity);
    }

    @Override
    public ServiceDTO getServiceById(Long id) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return serviceMapper.toDto(serviceEntity);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        List<ServiceEntity> services = serviceRepository.findAll();
        return services.stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDTO updateService(Long id, ServiceDTO serviceDTO) {
        ServiceEntity existingEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));

        existingEntity.setName(serviceDTO.getName());
        ServiceEntity updatedEntity = serviceRepository.save(existingEntity);
        return serviceMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteService(Long id) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceRepository.delete(serviceEntity);
    }
}