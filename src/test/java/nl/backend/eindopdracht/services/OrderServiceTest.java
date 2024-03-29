package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindopdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindopdracht.dtos.invoice.InvoiceOutputDto;
import nl.backend.eindopdracht.dtos.manageraccount.ManagerAccountOutputDto;
import nl.backend.eindopdracht.dtos.order.OrderInputDto;
import nl.backend.eindopdracht.dtos.order.OrderOutputDto;
import nl.backend.eindopdracht.exceptions.RecordNotFoundException;
import nl.backend.eindopdracht.models.*;
import nl.backend.eindopdracht.models.Order;
import nl.backend.eindopdracht.repositories.*;
import nl.backend.eindopdracht.utils.TypeOfWork;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    InvoiceRepository invoiceRepository;
    @Mock
    EmployeeAccountRepository employeeAccountRepository;
    @Mock
    ManagerAccountRepository managerAccountRepository;
    @Mock
    CustomerAccountRepository customerAccountRepository;
    @Mock
    InvoiceService invoiceService;
    @Mock
    CustomerAccountService customerAccountService;
    @Mock
    EmployeeAccountService employeeAccountService;
    @Mock
    ManagerAccountService managerAccountService;
    @InjectMocks
    OrderService orderService;

    List<Order> orders = new ArrayList<>();

    @BeforeEach
    void setup() {


        Order order1 = new Order();
        order1.setId(1);
        order1.setTypeOfWork(TypeOfWork.HUIS);
        order1.setAmount(20);
        order1.setPrice(300.00);
        order1.setProductName("bleek");
        order1.setStatus("in behandeling");
        order1.setDateCreated(LocalDate.of(2024, 10, 10));
        order1.setTime(LocalTime.of(8, 0));
        order1.setWorkAddress("Schoonmaakstraat 123");
        order1.setWorkZipcode("2548 DV");
        orders.add(order1);

        Order order2 = new Order();
        order2.setId(2);
        order2.setTypeOfWork(TypeOfWork.MEDISCH);
        order2.setAmount(10);
        order2.setPrice(400.00);
        order2.setProductName("WC-ROL");
        order2.setStatus("in behandeling");
        order2.setDateCreated(LocalDate.of(2024, 1, 10));
        order2.setTime(LocalTime.of(11, 0));
        order2.setWorkAddress("Schoonmaakstraat 123");
        order2.setWorkZipcode("2548 DV");
        orders.add(order2);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setTypeOfWork(TypeOfWork.HUIS);
        invoice.setPrice(200.00);
        invoice.setTypeOfProduct("schoonmaakmiddelen");
        invoice.setBusinessTaxNumber("123456789");
        invoice.setAmount(10);
        invoice.setTaxAmount(5000L);
        invoice.setDate(LocalDate.of(2024, 1, 1));
        invoice.setBusinessAddress("Maastraat");
        invoice.setCustomerAddress("Maasstraat");
        invoice.setTermOfPayment("DAGEN_30");
        order1.setInvoice(invoice);

        EmployeeAccount employeeAccount = new EmployeeAccount();
        employeeAccount.setId(1L);
        employeeAccount.setContractH(40.0);
        employeeAccount.setStartContract(LocalDate.of(2024, 1, 1));

        Set<EmployeeAccount> employeeAccounts = new HashSet<>();
        employeeAccounts.add(employeeAccount);
        order1.setEmployees(employeeAccounts);

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setId(1L);
        customerAccount.setCompanyName("Bedrijf");
        customerAccount.setContract("FulLTime");
        customerAccount.setBalans(10000.0);
        order1.setCustomerAccount(customerAccount);

        ManagerAccount managerAccount = new ManagerAccount();
        managerAccount.setId(1L);
        managerAccount.setResponsibilities("Algemeen Managament");

        Set<ManagerAccount> managerAccounts = new HashSet<>();
        managerAccounts.add(managerAccount);
        order1.setManagers(managerAccounts);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void orderTransferToDtoTest() {
        // Arrange
        Order order = orders.get(0);

        InvoiceOutputDto mockInvoiceDto = new InvoiceOutputDto();
        EmployeeAccountOutputDto mockEmployeeDto = new EmployeeAccountOutputDto();
        ManagerAccountOutputDto mockManagerDto = new ManagerAccountOutputDto();
        CustomerAccountOutputDto mockCustomerDto = new CustomerAccountOutputDto();

        when(invoiceService.invoiceTransferToDto(any(Invoice.class))).thenReturn(mockInvoiceDto);
        when(employeeAccountService.employeeAccountTransferToDto(any(EmployeeAccount.class))).thenReturn(mockEmployeeDto);
        when(managerAccountService.managerAccountTransferToDto(any(ManagerAccount.class))).thenReturn(mockManagerDto);
        when(customerAccountService.customerAccountTransferCustomerAccountOutputDto(any(CustomerAccount.class))).thenReturn(mockCustomerDto);

        // Act
        OrderOutputDto dto = orderService.orderTransferToDto(order);

        // Assert
        assertEquals(order.getId(), dto.getId());
        assertEquals(order.getTypeOfWork(), dto.getTypeOfWork());
        assertEquals(order.getAmount(), dto.getAmount());
        assertEquals(order.getPrice(), dto.getPrice());
        assertEquals(order.getProductName(), dto.getProductName());
        assertEquals(order.getStatus(), dto.getStatus());
        assertEquals(order.getDateCreated(), dto.getDateCreated());
        assertEquals(order.getTime(), dto.getTime());
        assertEquals(order.getWorkAddress(), dto.getWorkAddress());
        assertEquals(order.getWorkZipcode(), dto.getWorkZipcode());


        assertEquals(mockInvoiceDto, dto.getInvoiceOutputDto());
        assertNotNull(dto.getEmployees());
        assertTrue(dto.getEmployees().contains(mockEmployeeDto));
        assertNotNull(dto.getManagers());
        assertTrue(dto.getManagers().contains(mockManagerDto));
        assertEquals(mockCustomerDto, dto.getCustomerAccountOutputDto());
    }

    @Test
    void getAllOrders() {
        when(orderRepository.findAll()).thenReturn(orders);
        //Act
        List<OrderOutputDto> result = orderService.getAllOrders();
        //Assert
        assertEquals(orders.size(), result.size());
    }

    @Test
    void getOrderById() {

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orders.get(0)));

        OrderOutputDto orderDto = orderService.getOrderById(1L);

        assertEquals(orders.get(0).getTypeOfWork(), orderDto.getTypeOfWork());
        assertEquals(orders.get(0).getAmount(), orderDto.getAmount());
        assertEquals(orders.get(0).getPrice(), orderDto.getPrice());
        assertEquals(orders.get(0).getProductName(), orderDto.getProductName());
        assertEquals(orders.get(0).getStatus(), orderDto.getStatus());
        assertEquals(orders.get(0).getDateCreated(), orderDto.getDateCreated());
        assertEquals(orders.get(0).getTime(), orderDto.getTime());
        assertEquals(orders.get(0).getWorkAddress(), orderDto.getWorkAddress());
        assertEquals(orders.get(0).getWorkZipcode(), orderDto.getWorkZipcode());
    }

    @Test
    void recordNotFoundException() {
        assertThrows(RecordNotFoundException.class, () -> orderService.getOrderById(9L));
    }

    @Test
    void createOrder() {
        // Arrange
        OrderInputDto orderDto3 = new OrderInputDto();
        orderDto3.amount = 20;
        orderDto3.typeOfWork = TypeOfWork.HUIS;
        orderDto3.price = 60.00;
        orderDto3.productName = "WC-ROl";
        orderDto3.status = "afgehandeld";
        orderDto3.dateCreated = LocalDate.of(2024, 1, 2);
        orderDto3.time = LocalTime.of(12, 15);
        orderDto3.workAddress = "Scheldestraat 4";
        orderDto3.workZipcode = "1079 AZ";

        Order order = new Order();
        order.setAmount(orderDto3.amount);
        order.setTypeOfWork(orderDto3.typeOfWork);
        order.setPrice(orderDto3.price);
        order.setProductName(orderDto3.productName);
        order.setStatus(orderDto3.status);
        order.setDateCreated(orderDto3.dateCreated);
        order.setTime(orderDto3.time);
        order.setWorkAddress(orderDto3.workAddress);
        order.setWorkZipcode(orderDto3.workZipcode);
        order.setInvoice(order.getInvoice());

        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(order);

        // Act
        OrderOutputDto actualOrderOutputDto = orderService.createOrder(orderDto3);

        // Assert
        assertEquals(orderDto3.amount, actualOrderOutputDto.getAmount());
        assertEquals(orderDto3.typeOfWork, actualOrderOutputDto.getTypeOfWork());
        assertEquals(orderDto3.price, actualOrderOutputDto.getPrice());
        assertEquals(orderDto3.productName, actualOrderOutputDto.getProductName());
        assertEquals(orderDto3.status, actualOrderOutputDto.getStatus());
        assertEquals(orderDto3.dateCreated, actualOrderOutputDto.getDateCreated());
        assertEquals(orderDto3.time, actualOrderOutputDto.getTime());
        assertEquals(orderDto3.workAddress, actualOrderOutputDto.getWorkAddress());
        assertEquals(orderDto3.workZipcode, actualOrderOutputDto.getWorkZipcode());
    }

    @Test
    public void updateOrder() {
        long orderId = 1L;

        OrderInputDto orderDto4 = new OrderInputDto();
        orderDto4.amount = 20;
        orderDto4.typeOfWork = TypeOfWork.HUIS;
        orderDto4.price = 60.00;
        orderDto4.productName = "WC-ROl";
        orderDto4.status = "afgehandeld";
        orderDto4.dateCreated = LocalDate.of(2024, 1, 2);
        orderDto4.time = LocalTime.of(12, 15);
        orderDto4.workAddress = "Scheldestraat 4";
        orderDto4.workZipcode = "1079 AZ";

        Order excistingOrder = new Order();
        excistingOrder.setAmount(orderDto4.amount);
        excistingOrder.setTypeOfWork(orderDto4.typeOfWork);
        excistingOrder.setPrice(orderDto4.price);
        excistingOrder.setProductName(orderDto4.productName);
        excistingOrder.setStatus(orderDto4.status);
        excistingOrder.setDateCreated(orderDto4.dateCreated);
        excistingOrder.setTime(orderDto4.time);
        excistingOrder.setWorkAddress(orderDto4.workAddress);
        excistingOrder.setWorkZipcode(orderDto4.workZipcode);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(excistingOrder));
        when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(excistingOrder);

        // Act
        OrderOutputDto updateOrderOutputDto = orderService.updateOrder(orderId, orderDto4);

        //Assert
        assertEquals(orderDto4.amount, updateOrderOutputDto.getAmount());
        assertEquals(orderDto4.typeOfWork, updateOrderOutputDto.getTypeOfWork());
        assertEquals(orderDto4.price, updateOrderOutputDto.getPrice());
        assertEquals(orderDto4.productName, updateOrderOutputDto.getProductName());
        assertEquals(orderDto4.status, updateOrderOutputDto.getStatus());
        assertEquals(orderDto4.dateCreated, updateOrderOutputDto.getDateCreated());
        assertEquals(orderDto4.time, updateOrderOutputDto.getTime());
        assertEquals(orderDto4.workAddress, updateOrderOutputDto.getWorkAddress());
        assertEquals(orderDto4.workZipcode, updateOrderOutputDto.getWorkZipcode());

    }


    @Test
    public void deleteOrder() {
        orderService.deleteOrder(1L);

        verify(orderRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void updateOrder_RecordNotFound() {
        long orderId = 10L;
        OrderInputDto orderDto = new OrderInputDto();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            orderService.updateOrder(orderId, orderDto);
        });

        verify(orderRepository, never()).save(ArgumentMatchers.any(Order.class));
    }

    @Test
    public void assignInvoiceToOrder_BothFound() {
        long orderId = 1L;
        long invoiceId = 2L;

        Order order = new Order();
        Invoice invoice = new Invoice();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        orderService.assignInvoiceToOrder(orderId, invoiceId);

        assertEquals(invoice, order.getInvoice());
        verify(orderRepository).save(order);
    }

    @Test
    public void assignInvoiceToOrder_NotFound() {
        long orderId = 1L;
        long invoiceId = 2L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(new Invoice()));

        assertThrows(RecordNotFoundException.class, () -> {
            orderService.assignInvoiceToOrder(orderId, invoiceId);
        });
    }

    @Test
    public void assignEmployeesToOrder_BothFound() {
        long orderId = 1L;
        long employeeId = 2L;

        Order order = new Order();
        EmployeeAccount employee = new EmployeeAccount();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(employeeAccountRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        orderService.assignEmployeesToOrder(orderId, employeeId);

        Assertions.assertTrue(order.getEmployees().contains(employee));
        verify(orderRepository).save(order);
    }

    @Test
    public void assignEmployeesToOrder_NotFound() {
        long orderId = 1L;
        long employeeId = 2L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        when(employeeAccountRepository.findById(employeeId)).thenReturn(Optional.of(new EmployeeAccount()));

        assertThrows(RecordNotFoundException.class, () -> {
            orderService.assignEmployeesToOrder(orderId, employeeId);
        });
    }

    @Test
    public void assignManagerToOrder_BothFound() {
        long orderId = 1L;
        long managerId = 2L;

        Order order = new Order();
        ManagerAccount manager = new ManagerAccount();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(managerAccountRepository.findById(managerId)).thenReturn(Optional.of(manager));

        orderService.assignManagerToOrder(orderId, managerId);

        Assertions.assertTrue(order.getManagers().contains(manager));
        verify(orderRepository).save(order);
    }

    @Test
    public void assignManagerToOrder_NotFound() {
        long orderId = 1L;
        long managerId = 2L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        when(managerAccountRepository.findById(managerId)).thenReturn(Optional.of(new ManagerAccount()));

        assertThrows(RecordNotFoundException.class, () -> {
            orderService.assignManagerToOrder(orderId, managerId);
        });
    }

    @Test
    public void assignCustomerToOrder_BothFound() {
        long orderId = 1L;
        long customerId = 2L;

        Order order = new Order();
        CustomerAccount customer = new CustomerAccount();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(customerAccountRepository.findById(customerId)).thenReturn(Optional.of(customer));

        orderService.assignCustomerToOrder(orderId, customerId);

        assertEquals(customer, order.getCustomerAccount());
        verify(orderRepository).save(order);
    }

    @Test
    public void assignCustomerToOrder_NotFound() {
        long orderId = 1L;
        long customerId = 2L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        when(customerAccountRepository.findById(customerId)).thenReturn(Optional.of(new CustomerAccount()));

        assertThrows(RecordNotFoundException.class, () -> {
            orderService.assignCustomerToOrder(orderId, customerId);
        });

    }

}


