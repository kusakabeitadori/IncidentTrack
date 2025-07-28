package tech.noji.IncidentTrack.controller;

import tech.noji.IncidentTrack.dto.DepartementDTO;
import tech.noji.IncidentTrack.service.DepartementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceEntityController {
    private final DepartementService serviceEntityService;

    public ServiceEntityController(DepartementService serviceEntityService) {
        this.serviceEntityService = serviceEntityService;
    }

    @PostMapping
    public ResponseEntity<DepartementDTO> createService(@RequestBody DepartementDTO serviceDTO) {
        DepartementDTO createdService = serviceEntityService.createService(serviceDTO);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> getServiceById(@PathVariable Long id) {
        DepartementDTO serviceDTO = serviceEntityService.getServiceById(id);
        return ResponseEntity.ok(serviceDTO);
    }

    @GetMapping
    public ResponseEntity<List<DepartementDTO>> getAllServices() {
        List<DepartementDTO> services = serviceEntityService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartementDTO> updateService(@PathVariable Long id, @RequestBody DepartementDTO serviceDTO) {
        DepartementDTO updatedService = serviceEntityService.updateService(id, serviceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceEntityService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}