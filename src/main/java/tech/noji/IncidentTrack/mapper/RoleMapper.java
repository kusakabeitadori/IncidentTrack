package tech.noji.IncidentTrack.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.noji.IncidentTrack.dto.RoleDto;
import tech.noji.IncidentTrack.entite.Role;

@Component
public class RoleMapper {
    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDto toDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public Role toEntity(RoleDto roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }
}