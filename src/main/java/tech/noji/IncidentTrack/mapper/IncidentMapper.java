package tech.noji.IncidentTrack.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.*;
import tech.noji.IncidentTrack.entite.*;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.repository.UtilisateurRepository;

@Component
public class IncidentMapper {
    private final ModelMapper modelMapper;

    public IncidentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public IncidentDto toDto(Incident incident) {
        if (incident == null) {
            return null;
        }

        return modelMapper.map(incident, IncidentDto.class);
    }

    public Incident toEntity(CreationIncidentRequest request) {
        if (request == null) {
            return null;
        }
        return modelMapper.map(request,Incident.class);

    }

}