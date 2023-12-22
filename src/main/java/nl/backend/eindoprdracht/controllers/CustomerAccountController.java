package nl.backend.eindoprdracht.controllers;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.Getter;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountInputDto;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.models.CustomerAccount;
import nl.backend.eindoprdracht.services.CustomerAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindoprdracht.controllers.ControllerHelper.checkForBindingResult;

@RequestMapping("/customeraccount")
@RestController
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @PostMapping
    public ResponseEntity<CustomerAccountOutputDto> createCustomerAccount(@Valid @RequestBody CustomerAccountInputDto caInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            CustomerAccountOutputDto savedCustomerAccount = customerAccountService.createCustomerAccount(caInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + savedCustomerAccount.id).toUriString());
            return ResponseEntity.created(uri).body(savedCustomerAccount);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAccountOutputDto> getCustomerAccountId(@PathVariable Long id) {
        CustomerAccountOutputDto caDto = customerAccountService.getCustomerAccountId(id);
        return ResponseEntity.ok(caDto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerAccountOutputDto>> getAllCustomers() {
        List<CustomerAccountOutputDto> caDtoList = customerAccountService.getAllCustomers();
        return ResponseEntity.ok(caDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerAccountOutputDto> updateCustomerAccount(@PathVariable long id, @RequestBody CustomerAccountInputDto caInputDto) {
        CustomerAccountOutputDto dto = customerAccountService.updateCustomerAccount(id, caInputDto);
        return ResponseEntity.ok().body(dto);
    }
//TODO Als je alleen specifieke velden wilt bijwerken, overweeg dan om PATCH te gebruiken in plaats van PUT. PATCH is bedoeld voor gedeeltelijke updates.

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerAccount> deleteCustomerAccount(@PathVariable long id) {
        customerAccountService.deleteCustomerAccount(id);
        return ResponseEntity.noContent().build();
    }




}
