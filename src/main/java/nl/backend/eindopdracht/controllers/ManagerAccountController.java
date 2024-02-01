package nl.backend.eindopdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindopdracht.dtos.IdInputDto;
import nl.backend.eindopdracht.dtos.manageraccount.ManagerAccountInputDto;
import nl.backend.eindopdracht.dtos.manageraccount.ManagerAccountOutputDto;
import nl.backend.eindopdracht.exceptions.ValidationException;
import nl.backend.eindopdracht.models.ManagerAccount;
import nl.backend.eindopdracht.services.ManagerAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindopdracht.controllers.ControllerHelper.checkForBindingResult;

@RequestMapping("/manageraccount")
@RestController
public class ManagerAccountController {

    private final ManagerAccountService managerAccountService;

    public ManagerAccountController(ManagerAccountService managerAccountService) {
        this.managerAccountService = managerAccountService;
    }

    @PostMapping
    public ResponseEntity<ManagerAccountOutputDto> createManagerAccount(@Valid @RequestBody ManagerAccountInputDto maInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            ManagerAccountOutputDto savedManagerAccount = managerAccountService.createManagerAccount(maInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + savedManagerAccount.id).toUriString());
            return ResponseEntity.created(uri).body(savedManagerAccount);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerAccountOutputDto> getManagerAccountId(@PathVariable Long id) {
        ManagerAccountOutputDto maDto = managerAccountService.getManagerAccountId(id);
        return ResponseEntity.ok(maDto);
    }

    @GetMapping("/manageraccounts")
    public ResponseEntity<List<ManagerAccountOutputDto>> getAllManagers() {
        List<ManagerAccountOutputDto> maDtoList = managerAccountService.getAllManagers();
        return ResponseEntity.ok(maDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerAccountOutputDto> updateManagerAccount(@PathVariable long id, @RequestBody ManagerAccountInputDto managerAccountInputDto) {
        ManagerAccountOutputDto dto = managerAccountService.updateManagerAccount(id, managerAccountInputDto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("{id}/workschedule")
    public ResponseEntity<ManagerAccountOutputDto> assignManagerToWorkSchedule(@PathVariable long id, @RequestBody IdInputDto input){
        managerAccountService.assignManagerToWorkSchedule(id, input.id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ManagerAccount> deleteManagerAccount(@PathVariable long id) {
        managerAccountService.deleteManagerAccount(id);
        return ResponseEntity.noContent().build();
    }
}
