package tech.noji.IncidentTrack.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.ServiceDTO;
import tech.noji.IncidentTrack.entite.ServiceEntity;

@Component
public class ServiceMapper {
    private final ModelMapper modelMapper;

    public ServiceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ServiceDTO toDto(ServiceEntity serviceEntity) {
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }

    public ServiceEntity toEntity(ServiceDTO serviceDTO) {
        return modelMapper.map(serviceDTO, ServiceEntity.class);
    }
}