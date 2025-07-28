package tech.noji.IncidentTrack.mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.CreationUtilisateurRequestDto;
import tech.noji.IncidentTrack.dto.UtilisateurDto;
import tech.noji.IncidentTrack.entite.Utilisateur;

@Component
public class UtilisateurMapper {
    private final ModelMapper modelMapper;

    public UtilisateurMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public UtilisateurDto toDto(Utilisateur user) {
        return modelMapper.map(user, UtilisateurDto.class);
    }

    public Utilisateur toEntity(CreationUtilisateurRequestDto requestDto) {
        return modelMapper.map(requestDto, Utilisateur.class);
    }
}