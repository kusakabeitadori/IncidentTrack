package tech.noji.IncidentTrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.noji.IncidentTrack.dto.HistoriqueIncidentDto;
import tech.noji.IncidentTrack.service.HistoriqueService;
import java.util.List;

@RestController
@RequestMapping("/api/historique")
@RequiredArgsConstructor
public class HistoriqueController {
    private final HistoriqueService historiqueService;


    @GetMapping("/incident/{incidentId}")
    public ResponseEntity<List<HistoriqueIncidentDto>> getHistoriqueByIncident(
            @PathVariable Long incidentId) {
        return ResponseEntity.ok(historiqueService.getHistoriqueIncident(incidentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueIncidentDto> getHistoriqueEntry(
            @PathVariable Long id) {
        return ResponseEntity.ok(historiqueService.getHistoriqueEntry(id));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteHistoriqueEntry(
            @PathVariable Long id) {
        historiqueService.deleteHistoriqueEntry(id);
        return ResponseEntity.noContent().build();
    }
}