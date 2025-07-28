package tech.noji.IncidentTrack.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.HistoriqueIncidentDto;
import tech.noji.IncidentTrack.entite.HistoriqueIncident;

@Component
public class HistoriqueMapper {
    private final ModelMapper modelMapper;

    public HistoriqueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public HistoriqueIncidentDto toDto(HistoriqueIncident historique) {
        return modelMapper.map(historique, HistoriqueIncidentDto.class);
    }

    public HistoriqueIncident toEntity(HistoriqueIncidentDto dto) {
        return modelMapper.map(dto, HistoriqueIncident.class);
    }
}