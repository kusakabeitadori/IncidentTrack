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
    private final UtilisateurRepository utilisateurRepository;

    public IncidentMapper(ModelMapper modelMapper, UtilisateurRepository utilisateurRepository) {
        this.modelMapper = modelMapper;
        this.utilisateurRepository = utilisateurRepository;
        this.configureBasicMappings();
    }

    private void configureBasicMappings() {
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    public IncidentDto toDto(Incident incident) {
        if (incident == null) {
            return null;
        }

        IncidentDto dto = modelMapper.map(incident, IncidentDto.class);

        if (incident.getUtilisateurAssigne() != null) {
            dto.setUtilisateurAssigneId(incident.getUtilisateurAssigne().getId());
            dto.setUtilisateurAssigneNom(incident.getUtilisateurAssigne().getNom());
            if (incident.getUtilisateurAssigne().getService() != null) {
                dto.setUtilisateurAssigneServiceNom(incident.getUtilisateurAssigne().getService().getName());
            }
        }

        if (incident.getCreateurIncident() != null) {
            dto.setCreateurIncidentId(incident.getCreateurIncident().getId());
            dto.setCreateurIncidentNom(incident.getCreateurIncident().getNom());
        }

        dto.setStatus(incident.getStatus().name());
        dto.setCreationDate(incident.getCreationDate());

        return dto;
    }

    public Incident toEntity(CreationIncidentRequest request) {
        if (request == null) {
            return null;
        }

        Incident incident = new Incident();
        incident.setTitre(request.getTitre());
        incident.setDescription(request.getDescription());

        if (request.getUtilisateurAssigneId() != null) {
            Utilisateur utilisateurAssigne = utilisateurRepository.findById(request.getUtilisateurAssigneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur assigné non trouvé"));
            incident.setUtilisateurAssigne(utilisateurAssigne);
        }

        if (request.getCreateurIncidentId() != null) {
            Utilisateur createur = utilisateurRepository.findById(request.getCreateurIncidentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Créateur de l'incident non trouvé"));
            incident.setCreateurIncident(createur);
        }

        return incident;
    }

    public void updateIncidentFromDto(UpdateIncidentRequest request, Incident incident) {
        if (request.getTitre() != null) {
            incident.setTitre(request.getTitre());
        }
        if (request.getDescription() != null) {
            incident.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            incident.setStatus(request.getStatus());
        }
        if (request.getUtilisateurAssigneId() != null) {
            Utilisateur assigne = utilisateurRepository.findById(request.getUtilisateurAssigneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur assigné non trouvé"));
            incident.setUtilisateurAssigne(assigne);
        }
    }
}