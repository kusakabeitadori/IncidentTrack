package tech.noji.IncidentTrack.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.noji.IncidentTrack.dto.CreationUtilisateurRequestDto;
import tech.noji.IncidentTrack.dto.UtilisateurDto;
import tech.noji.IncidentTrack.service.UserService;
//import tech.noji.IncidentTrack.service.impl.AdminServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService adminService;

    @PostMapping("/")
    public ResponseEntity<UtilisateurDto> createUser(@RequestBody CreationUtilisateurRequestDto requestDto) {
        return ResponseEntity.ok(adminService.createUser(requestDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<UtilisateurDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> updateUser(@PathVariable Long id,
                                                     @RequestBody CreationUtilisateurRequestDto requestDto) {
        return ResponseEntity.ok(adminService.updateUser(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{utilisateurId}/role/{roleId}")
    public ResponseEntity<String> attribuerRole(
            @PathVariable Long utilisateurId,
            @PathVariable Long roleId) {

        adminService.attribuerRole(utilisateurId, roleId);
        return ResponseEntity.ok("Rôle attribué avec succès à l'utilisateur.");
    }

    @PutMapping("/{utilisateurId}/service/{serviceId}")
    public ResponseEntity<String> attribuerService(
            @PathVariable Long utilisateurId,
            @PathVariable Long serviceId) {
        adminService.attribuerService(utilisateurId, serviceId);
        return ResponseEntity.ok("Service attribué avec succès à l'utilisateur.");
    }
}
