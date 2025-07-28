package tech.noji.IncidentTrack.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.DepartementDTO;
import tech.noji.IncidentTrack.entite.Departement;

@Component
public class ServiceMapper {
    private final ModelMapper modelMapper;

    public ServiceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DepartementDTO toDto(Departement serviceEntity) {
        return modelMapper.map(serviceEntity, DepartementDTO.class);
    }

    public Departement toEntity(DepartementDTO serviceDTO) {
        return modelMapper.map(serviceDTO, Departement.class);
    }
}