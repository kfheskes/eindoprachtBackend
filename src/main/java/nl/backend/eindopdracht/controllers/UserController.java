package nl.backend.eindopdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindopdracht.dtos.IdInputDto;
import nl.backend.eindopdracht.dtos.role.RoleInputDto;
import nl.backend.eindopdracht.dtos.user.UserInputDto;
import nl.backend.eindopdracht.dtos.user.UserOutputDto;
import nl.backend.eindopdracht.exceptions.ValidationException;
import nl.backend.eindopdracht.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static nl.backend.eindopdracht.controllers.ControllerHelper.checkForBindingResult;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto dto, BindingResult br) {
        if (br.hasFieldErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            UserOutputDto userDto = userService.createUser(dto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + userDto.getId()).toUriString());
            return ResponseEntity.created(uri).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or #id == principal.id")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable long id, Principal principal) {
        UserOutputDto outputDto = userService.getUser(id);
        return ResponseEntity.ok().body(outputDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserOutputDto>> getUsers() {
        List<UserOutputDto> userOutputDtoList = userService.getUsers();

        return ResponseEntity.ok().body(userOutputDtoList);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or #id == principal.id")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable long id, @RequestBody UserInputDto inputDto) {
        UserOutputDto outputDto = userService.updateUser(id, inputDto);
        return ResponseEntity.ok().body(outputDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or #id == principal.id")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/{roleName}")
    public ResponseEntity<Void> deleteRole(@PathVariable long id, @PathVariable String roleName) {
        userService.removeRole(id, roleName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserOutputDto> addRoleToUser(@PathVariable long id, @RequestBody RoleInputDto roleName) {
        userService.addRoleToUser(id, roleName.getRoleName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userName}/employee")
    public ResponseEntity<UserOutputDto> assignEmployeeToUser(@PathVariable String userName, @RequestBody IdInputDto input) {
        userService.assignUserToEmployeeAccount(userName, input.id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userName}/manager")
    public ResponseEntity<UserOutputDto> assignManagerToUser(@PathVariable String userName, @RequestBody IdInputDto input) {
        userService.assignUserToManagerAccount(userName, input.id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userName}/customer")
    public ResponseEntity<UserOutputDto> assignCustomerToUser(@PathVariable String userName, @RequestBody IdInputDto input) {
        userService.assignUserToCustomerAccount(userName, input.id);
        return ResponseEntity.noContent().build();
    }

}

