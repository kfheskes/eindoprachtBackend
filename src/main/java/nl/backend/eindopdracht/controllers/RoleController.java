package nl.backend.eindopdracht.controllers;

import nl.backend.eindopdracht.dtos.role.RoleOutputDto;
import nl.backend.eindopdracht.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleOutputDto>> getAllRoles() {
        List<RoleOutputDto> roleOutputDtoList = roleService.getAllRoles();
        return ResponseEntity.ok(roleOutputDtoList);
    }


}
