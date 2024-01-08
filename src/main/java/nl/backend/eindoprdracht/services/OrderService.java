package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.dtos.order.OrderInputDto;
import nl.backend.eindoprdracht.dtos.order.OrderOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.EmployeeAccount;
import nl.backend.eindoprdracht.models.Invoice;
import nl.backend.eindoprdracht.models.Order;
import nl.backend.eindoprdracht.repositories.EmployeeAccountRepository;
import nl.backend.eindoprdracht.repositories.InvoiceRepository;
import nl.backend.eindoprdracht.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final InvoiceRepository invoiceRepository;

    private final InvoiceService invoiceService;

    private final EmployeeAccountRepository employeeAccountRepository;

    private final EmployeeAccountService employeeAccountService;

    public OrderService(OrderRepository orderRepository, InvoiceRepository invoiceRepository, InvoiceService invoiceService, EmployeeAccountRepository employeeAccountRepository, EmployeeAccountService employeeAccountService) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.employeeAccountRepository = employeeAccountRepository;
        this.employeeAccountService = employeeAccountService;
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

        if (order.getInvoice() != null){
            dto.setInvoiceOutputDto(invoiceService.invoiceTransferToDto(order.getInvoice()));
        }
        if (order.getEmployees() != null) {
            Set<EmployeeAccountOutputDto> employeeAccountOutputDtos = new HashSet<>();
            for(EmployeeAccount ea : order.getEmployees()){
                employeeAccountOutputDtos.add(employeeAccountService.employeeAccountTransferToDto(ea));
            }
            dto.setEmployees(employeeAccountOutputDtos);
        }

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


    public void assignInvoiceToOrder(long orderId, long invoiceId ){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<Invoice>  optionalInvoice = invoiceRepository.findById(invoiceId);

        if (optionalOrder.isPresent() && optionalInvoice.isPresent()) {
            Order order = optionalOrder.get();
            Invoice invoice = optionalInvoice.get();

            order.setInvoice(invoice);

            orderRepository.save(order);
        } else {
            throw new RecordNotFoundException("no order or invoice has found");
        }
    }

    public void assignEmployeesToOrder(long orderId, long employeesId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findById(employeesId);

        if (optionalOrder.isPresent() && optionalEmployeeAccount.isPresent()) {
            Order order = optionalOrder.get();
            EmployeeAccount employee = optionalEmployeeAccount.get();
            order.getEmployees().add(employee);
            orderRepository.save(order);
        } else {
            throw new RecordNotFoundException("No combination emplyee work schedule is found");
        }
    }

}
