package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.order.OrderInputDto;
import nl.backend.eindoprdracht.dtos.order.OrderOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.Order;
import nl.backend.eindoprdracht.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order dtoTransferToOrder(OrderInputDto dto) {
        Order order = new Order();

        order.setTypeOfWork(dto.getTypeOfWork());
        order.setAmount(dto.getAmount());
        order.setPrice(dto.getPrice());
        order.setProductId(dto.getProductId());
        order.setProductName(dto.getProductName());
        order.setCustomerName(dto.getCustomerName());
        order.setStatus(dto.getStatus());
        order.setDateCreated(dto.getDateCreated());
        order.setTime(dto.getTime());
        order.setWorkAddress(dto.getWorkAddress());
        order.setWorkZipcode(dto.getWorkZipcode());

        return order;
    }

    public OrderOutputDto orderTransferToDto(Order order) {
        OrderOutputDto dto = new OrderOutputDto();

        dto.setId(order.getId());
        dto.setTypeOfWork(order.getTypeOfWork());
        dto.setAmount(order.getAmount());
        dto.setPrice(order.getPrice());
        dto.setProductId(order.getProductId());
        dto.setProductName(order.getProductName());
        dto.setCustomerName(order.getCustomerName());
        dto.setStatus(order.getStatus());
        dto.setDateCreated(order.getDateCreated());
        dto.setTime(order.getTime());
        dto.setWorkAddress(order.getWorkAddress());
        dto.setWorkZipcode(order.getWorkZipcode());

        return dto;
    }

    public OrderOutputDto createOrder(OrderInputDto dto) {
        Order order = dtoTransferToOrder(dto);
        orderRepository.save(order);
        return orderTransferToDto(order);
    }

    public OrderOutputDto getOrderById(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order o = order.get();
            OrderOutputDto dto = orderTransferToDto(o);
            return dto;
        } else {
            throw new RecordNotFoundException("No order found with id: " + id);
        }
    }

    public List<OrderOutputDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderOutputDto> dtoList = new ArrayList<>();
        for (Order order : orders) {
            dtoList.add(orderTransferToDto(order));
        }
        return dtoList;
    }

    public OrderOutputDto updateOrder(long id, OrderInputDto dto) {
        Optional<Order> getOrder = orderRepository.findById(id);
        if (getOrder.isEmpty()) {
            throw new RecordNotFoundException("No order found by id " + id);
        } else {
            Order orderToUpdate = getOrder.get();

            if (dto.getTypeOfWork() != null) {
                orderToUpdate.setTypeOfWork(dto.getTypeOfWork());
            }
            if (dto.getAmount() != null) {
                orderToUpdate.setAmount(dto.getAmount());
            }
            if (dto.getPrice() != null) {
                orderToUpdate.setPrice(dto.getPrice());
            }
            if (dto.getProductId() != null) {
                orderToUpdate.setProductId(dto.getProductId());
            }
            if (dto.getProductName() != null) {
                orderToUpdate.setProductName(dto.getProductName());
            }
            if (dto.getCustomerName() != null) {
                orderToUpdate.setCustomerName(dto.getCustomerName());
            }
            if (dto.getStatus() != null) {
                orderToUpdate.setStatus(dto.getStatus());
            }
            if (dto.getDateCreated() != null) {
                orderToUpdate.setDateCreated(dto.getDateCreated());
            }
            if (dto.getTime() != null) {
                orderToUpdate.setTime(dto.getTime());
            }
            if (dto.getWorkAddress() != null) {
                orderToUpdate.setWorkAddress(dto.getWorkAddress());
            }
            if (dto.getWorkZipcode() != null) {
                orderToUpdate.setWorkZipcode(dto.getWorkZipcode());
            }
            Order updatedOrder = orderRepository.save(orderToUpdate);
            return orderTransferToDto(updatedOrder);
        }
    }


    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
