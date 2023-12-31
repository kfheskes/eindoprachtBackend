package nl.backend.eindoprdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindoprdracht.dtos.IdInputDto;
import nl.backend.eindoprdracht.dtos.order.OrderInputDto;
import nl.backend.eindoprdracht.dtos.order.OrderOutputDto;
import nl.backend.eindoprdracht.exceptions.ValidationException;
import nl.backend.eindoprdracht.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindoprdracht.controllers.ControllerHelper.checkForBindingResult;

@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderOutputDto> createOrder(@Valid @RequestBody OrderInputDto orderInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            throw new ValidationException(checkForBindingResult(br));
        } else {
            OrderOutputDto savedOrder = orderService.createOrder(orderInputDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + savedOrder.getId()).toUriString());
            return ResponseEntity.created(uri).body(savedOrder);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        OrderOutputDto orderDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<OrderOutputDto> orderDtoList = orderService.getAllOrders();
        return ResponseEntity.ok(orderDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutputDto> updateOrder(@PathVariable long id, @RequestBody OrderInputDto orderInputDto) {
        OrderOutputDto dto = orderService.updateOrder(id, orderInputDto);
        return ResponseEntity.ok().body(dto);
    }

    //TODO Rowan vragen voor uitleg one to one relatie plus input.id
    //TODO Rowan wanneer gebruik maken van Cascade??
    @PutMapping("/{id}/invoices")
    public ResponseEntity<OrderOutputDto> assignInvoiceToOrder(@PathVariable long id, @RequestBody IdInputDto input ) {
        orderService.assignInvoiceToOrder(id, input.id);
        return ResponseEntity.noContent().build();
    }

    //TODO uitleg vragen many to many
    @PutMapping("{id}/employees")
    public ResponseEntity<OrderOutputDto> assignEmployeesToOrder(@PathVariable long id, @RequestBody IdInputDto input) {
        orderService.assignEmployeesToOrder(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/managers")
    public ResponseEntity<OrderOutputDto> assignManagerToOrder(@PathVariable long id, @RequestBody IdInputDto input) {
        orderService.assignManagerToOrder(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/customers")
    public ResponseEntity<OrderOutputDto> assignCustomerToOrder (@PathVariable long id, @RequestBody IdInputDto input) {
        orderService.assignCustomerToOrder(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
