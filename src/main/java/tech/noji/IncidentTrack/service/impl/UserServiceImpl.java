package tech.noji.IncidentTrack.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.noji.IncidentTrack.dto.*;
import tech.noji.IncidentTrack.entite.*;
import tech.noji.IncidentTrack.exception.ResourceNotFoundException;
import tech.noji.IncidentTrack.mapper.UtilisateurMapper;
import tech.noji.IncidentTrack.repository.*;
import tech.noji.IncidentTrack.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final DepartementRepository serviceRepository;
    private final UtilisateurMapper utilisateurMapper;

    @Override
    public UtilisateurDto createUser(CreationUtilisateurRequestDto requestDto) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(requestDto);

        Role role = roleRepository.findById(requestDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role non trouvé"));
        utilisateur.setRole(role);

        if (requestDto.getServiceId() != null) {
            Departement service = serviceRepository.findById(requestDto.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));
            utilisateur.setService(service);
        }

        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(savedUser);
    }

    @Override
    public List<UtilisateurDto> getAllUsers() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto getUserById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return utilisateurMapper.toDto(utilisateur);
    }

    @Override
    public void deleteUser(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public void attribuerRole(Long utilisateurId, Long roleId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role non trouvé"));

        utilisateur.setRole(role);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void attribuerService(Long utilisateurId, Long serviceId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Departement service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));

        utilisateur.setService(service);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public UtilisateurDto updateUser(Long id, CreationUtilisateurRequestDto requestDto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        utilisateur.setNom(requestDto.getNom());
        utilisateur.setEmail(requestDto.getEmail());

        if (requestDto.getRoleId() != null) {
            Role role = roleRepository.findById(requestDto.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role non trouvé"));
            utilisateur.setRole(role);
        }

        if (requestDto.getServiceId() != null) {
            Departement service = serviceRepository.findById(requestDto.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service non trouvé"));
            utilisateur.setService(service);
        }

        Utilisateur updatedUser = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(updatedUser);
    }

}