//package tech.noji.IncidentTrack.controller;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import tech.noji.IncidentTrack.dto.CreationIncidentRequest;
//import tech.noji.IncidentTrack.dto.IncidentDto;
//import tech.noji.IncidentTrack.service.IncidentService;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
//
//@RestController
//@RequestMapping("/api/incidents")
//public class IncidentController{
//
//    private final IncidentService incidentService;
//    public IncidentController(IncidentService incidentService) {
//        this.incidentService = incidentService;
//    }
//
//    @PostMapping
//    public ResponseEntity<IncidentDto> createIncident(@RequestBody CreationIncidentRequest request)
//    {
//        return ResponseEntity.ok(incidentService.createAndAssignIncident(request));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<IncidentDto>> getAllIncidents()
//    {
//        return ResponseEntity.ok(incidentService.getAllIncidents());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable  Long id)
//    {
//        return ResponseEntity.ok(incidentService.getIncidentById(id));
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<IncidentDto> reassignIncident(@PathVariable Long id, @RequestBody Map<String, Long> request) {
//        return ResponseEntity.ok(incidentService.reassignIncident(id, request.get("utilisateurAssigneId")));
//    }
//
//
//    @PutMapping("/{id}/status")
//    public  ResponseEntity<IncidentDto> updateStatus(@PathVariable Long id,@RequestBody Map<String, String> statutUpdate)
//    {
//        return ResponseEntity.ok(incidentService.changeStatus(id, statutUpdate.get("statut")));
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteIncident(@PathVariable Long id)
//    {
//        incidentService.deleteIncident(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/agent/{UtilisateurAssigneId}")
//    public ResponseEntity<List<IncidentDto>> getIncidentsByAgent(@PathVariable Long AgentTechAssigneId) {
//        return ResponseEntity.ok(incidentService.getIncidentsByAgent(AgentTechAssigneId));
//    }
//
//
//}


package tech.noji.IncidentTrack.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.noji.IncidentTrack.dto.*;
import tech.noji.IncidentTrack.service.IncidentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<IncidentDto> createIncident(@RequestBody CreationIncidentRequest request) {
        return ResponseEntity.ok(incidentService.createAndAssignIncident(request));
    }

    @GetMapping
    public ResponseEntity<List<IncidentDto>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentDto> updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentRequest request) {
        return ResponseEntity.ok(incidentService.updateIncident(id, request));
    }

    @PutMapping("/{id}/reassign")
    public ResponseEntity<IncidentDto> reassignIncident(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        return ResponseEntity.ok(incidentService.reassignIncident(id, request.get("utilisateurAssigneId")));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<IncidentDto> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(incidentService.changeStatus(id, request.getStatut(), request.getDetails()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<IncidentDto>> getIncidentsByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(incidentService.getIncidentsByAgent(agentId));
    }
}