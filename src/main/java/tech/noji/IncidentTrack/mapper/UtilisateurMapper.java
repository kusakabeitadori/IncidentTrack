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
        configureMappings();
    }

    private void configureMappings() {

        modelMapper.typeMap(CreationUtilisateurRequestDto.class, Utilisateur.class)
                .addMappings(mapper -> {
                    mapper.skip(Utilisateur::setId);
//                    mapper.map(src -> src.getRoleId(), (dest, v) -> dest.getRole().setId((Long)v));
//                    mapper.map(src -> src.getServiceId(), (dest, v) -> dest.getService().setId((Long)v));
                });
    }

    public UtilisateurDto toDto(Utilisateur user) {
        UtilisateurDto dto = modelMapper.map(user, UtilisateurDto.class);

        if (user.getRole() != null) {
            dto.setRoleNom(user.getRole().getName());
        }

//        if (user.getService() != null) {
////            dto.setServiceNom(user.getService().getName());
//        }

        return dto;
    }

    public Utilisateur toEntity(CreationUtilisateurRequestDto requestDto) {
        return modelMapper.map(requestDto, Utilisateur.class);
    }
}