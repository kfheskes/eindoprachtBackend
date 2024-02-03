package nl.backend.eindopdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindopdracht.dtos.IdInputDto;
import nl.backend.eindopdracht.dtos.employeeaccount.EmployeeAccountInputDto;
import nl.backend.eindopdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindopdracht.exceptions.ValidationException;
import nl.backend.eindopdracht.models.EmployeeAccount;
import nl.backend.eindopdracht.services.EmployeeAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindopdracht.controllers.ControllerHelper.checkForBindingResult;

@RequestMapping("/employeeaccount")
@RestController
public class EmployeeAccountController {

    private final EmployeeAccountService employeeAccountService;

    public EmployeeAccountController(EmployeeAccountService employeeAccountService) {
        this.employeeAccountService = employeeAccountService;
    }


    @PostMapping
    public ResponseEntity<EmployeeAccountOutputDto>
    createEmployeeAccount(@Valid @RequestBody EmployeeAccountInputDto caInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            EmployeeAccountOutputDto savedCustomerAccount = employeeAccountService.createEmployeeAccount(caInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + savedCustomerAccount.id).toUriString());
            return ResponseEntity.created(uri).body(savedCustomerAccount);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAccountOutputDto>
    getEmployeeAccountId(@PathVariable Long id) {
        EmployeeAccountOutputDto eaDto = employeeAccountService.getEmployeeAccountId(id);
        return ResponseEntity.ok(eaDto);
    }

    @GetMapping("/employeeaccounts")
    public ResponseEntity<List<EmployeeAccountOutputDto>> getAllEmployees() {
        List<EmployeeAccountOutputDto> eaDtoList = employeeAccountService.getAllEmployees();
        return ResponseEntity.ok(eaDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeAccountOutputDto>
    updateEmployeeAccount(@PathVariable long id, @RequestBody EmployeeAccountInputDto employeeAccountInputDto) {
        EmployeeAccountOutputDto dto = employeeAccountService.updateEmployeeAccount(id, employeeAccountInputDto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("{id}/workschedule")
    public ResponseEntity<EmployeeAccountOutputDto> assignEmployeeToWorkSchedule(@PathVariable long id, @RequestBody IdInputDto input) {
        employeeAccountService.assignEmployeesToWorkSchedule(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeAccount> deleteEmployeeAccount(@PathVariable long id) {
        employeeAccountService.deleteEmployeeAccount(id);
        return ResponseEntity.noContent().build();

    }

}
