package tech.noji.IncidentTrack.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgentWithIncidentsDto {
    private Long id;
    private UtilisateurDto agentTech;
    private List<IncidentDto> incidents;

}
