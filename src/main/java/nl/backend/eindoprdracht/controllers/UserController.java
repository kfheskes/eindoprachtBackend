package nl.backend.eindoprdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindoprdracht.dtos.order.OrderInputDto;
import nl.backend.eindoprdracht.dtos.order.OrderOutputDto;
import nl.backend.eindoprdracht.dtos.user.UserInputDto;
import nl.backend.eindoprdracht.dtos.user.UserOutputDto;
import nl.backend.eindoprdracht.exceptions.ValidationException;
import nl.backend.eindoprdracht.models.User;
import nl.backend.eindoprdracht.services.OrderService;
import nl.backend.eindoprdracht.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindoprdracht.controllers.ControllerHelper.checkForBindingResult;


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

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getUsers(){
        List<UserOutputDto> userOutputDtoList = userService.getUsers();

        return ResponseEntity.ok().body(userOutputDtoList);
    }


@PutMapping("/{id}")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable long id, @RequestBody UserInputDto inputDto){
        UserOutputDto outputDto = userService.updateUser(id, inputDto);
return ResponseEntity.ok().body(outputDto);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/{roleName}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id, @PathVariable String roleName){
        userService.removeRole(id,roleName);
        return ResponseEntity.noContent().build();
    }


}

