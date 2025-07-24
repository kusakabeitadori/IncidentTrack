package tech.noji.IncidentTrack.service;

import tech.noji.IncidentTrack.dto.RoleDto;
import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDTO);
    RoleDto getRoleById(Long id);
    List<RoleDto> getAllRoles();
    RoleDto updateRole(Long id, RoleDto roleDTO);
    void deleteRole(Long id);
}