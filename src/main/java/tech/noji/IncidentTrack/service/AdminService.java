package tech.noji.IncidentTrack.service;

import tech.noji.IncidentTrack.dto.*;
import java.util.List;

public interface AdminService {
    UtilisateurDto createUser(CreationUtilisateurRequestDto requestDto);
    List<UtilisateurDto> getAllUsers();
    UtilisateurDto getUserById(Long id);
    void deleteUser(Long id);
    void attribuerRole(Long utilisateurId, Long roleId);
    void attribuerService(Long utilisateurId, Long serviceId);
    UtilisateurDto updateUser(Long id, CreationUtilisateurRequestDto requestDto);
}