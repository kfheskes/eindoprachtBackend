package nl.backend.eindopdracht.controllers;


import jakarta.validation.Valid;
import nl.backend.eindopdracht.dtos.IdInputDto;
import nl.backend.eindopdracht.dtos.invoice.InvoiceInputDto;
import nl.backend.eindopdracht.dtos.invoice.InvoiceOutputDto;
import nl.backend.eindopdracht.exceptions.ValidationException;
import nl.backend.eindopdracht.models.Invoice;
import nl.backend.eindopdracht.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindopdracht.controllers.ControllerHelper.checkForBindingResult;

@RequestMapping("/invoice")
@RestController
public class InvoiceController {


    private final InvoiceService invoiceService;


    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceOutputDto> createInvoice(@Valid @RequestBody InvoiceInputDto invoiceInputDto, BindingResult br ) {
        if (br.hasFieldErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            InvoiceOutputDto savedInvoice = invoiceService.createInvoice(invoiceInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + savedInvoice.id).toUriString());
            return ResponseEntity.created(uri).body(savedInvoice);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto> getInvoice (@PathVariable long id){
        InvoiceOutputDto invoiceDto = invoiceService.getInvoice(id);
        return ResponseEntity.ok(invoiceDto);
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceOutputDto>> getAllInvoice (){
        List<InvoiceOutputDto> invoiceOutputDtoList = invoiceService.getAllInvoice();
        return ResponseEntity.ok(invoiceOutputDtoList);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<InvoiceOutputDto>> getAllInvoicesByCustomerID(@PathVariable long customerId) {
        List<InvoiceOutputDto> invoiceOutputDtoList = invoiceService.getAllInvoiceByCustomerId(customerId);
        return ResponseEntity.ok(invoiceOutputDtoList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<InvoiceOutputDto>
    updateInvoice(@PathVariable long id, @RequestBody InvoiceInputDto invoiceInputDto) {
        InvoiceOutputDto dto = invoiceService.updateInvoice(id, invoiceInputDto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("{id}/customer")
    public ResponseEntity<InvoiceOutputDto> assignCustomerToInvoice(@PathVariable long id, @RequestBody IdInputDto input) {
        invoiceService.assignCustomerToInvoice(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice>
    deleteInvoice(@PathVariable long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

}
