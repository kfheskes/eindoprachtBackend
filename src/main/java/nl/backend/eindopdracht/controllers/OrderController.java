package nl.backend.eindopdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindopdracht.dtos.IdInputDto;
import nl.backend.eindopdracht.dtos.order.OrderInputDto;
import nl.backend.eindopdracht.dtos.order.OrderOutputDto;
import nl.backend.eindopdracht.exceptions.ValidationException;
import nl.backend.eindopdracht.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static nl.backend.eindopdracht.controllers.ControllerHelper.checkForBindingResult;

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

    @GetMapping("/orders")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<OrderOutputDto> orderDtoList = orderService.getAllOrders();
        return ResponseEntity.ok(orderDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutputDto> updateOrder(@PathVariable long id, @RequestBody OrderInputDto orderInputDto) {
        OrderOutputDto dto = orderService.updateOrder(id, orderInputDto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}/invoices")
    public ResponseEntity<OrderOutputDto> assignInvoiceToOrder(@PathVariable long id, @RequestBody IdInputDto input) {
        orderService.assignInvoiceToOrder(id, input.id);
        return ResponseEntity.noContent().build();
    }

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
    public ResponseEntity<OrderOutputDto> assignCustomerToOrder(@PathVariable long id, @RequestBody IdInputDto input) {
        orderService.assignCustomerToOrder(id, input.id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
