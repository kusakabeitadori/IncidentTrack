package tech.noji.IncidentTrack.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.noji.IncidentTrack.dto.DepartementDTO;
import tech.noji.IncidentTrack.entite.Departement;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.ServiceMapper;
import tech.noji.IncidentTrack.repository.DepartementRepository;
import tech.noji.IncidentTrack.service.DepartementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartementServiceImpl implements DepartementService {
    private final DepartementRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public DepartementServiceImpl(DepartementRepository serviceRepository,
                                  ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public DepartementDTO createService(DepartementDTO serviceDTO) {
        Departement serviceEntity = serviceMapper.toEntity(serviceDTO);
        Departement savedEntity = serviceRepository.save(serviceEntity);
        return serviceMapper.toDto(savedEntity);
    }

    @Override
    public DepartementDTO getServiceById(Long id) {
        Departement serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return serviceMapper.toDto(serviceEntity);
    }

    @Override
    public List<DepartementDTO> getAllServices() {
        List<Departement> services = serviceRepository.findAll();
        return services.stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartementDTO updateService(Long id, DepartementDTO serviceDTO) {
        Departement existingEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));

        existingEntity.setName(serviceDTO.getName());
        Departement updatedEntity = serviceRepository.save(existingEntity);
        return serviceMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteService(Long id) {
        Departement serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceRepository.delete(serviceEntity);
    }
}