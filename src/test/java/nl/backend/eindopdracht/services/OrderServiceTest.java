package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.order.OrderOutputDto;
import nl.backend.eindopdracht.exceptions.RecordNotFoundException;
import nl.backend.eindopdracht.models.File;
import nl.backend.eindopdracht.models.Invoice;
import nl.backend.eindopdracht.models.Order;
import nl.backend.eindopdracht.repositories.CustomerAccountRepository;
import nl.backend.eindopdracht.repositories.InvoiceRepository;
import nl.backend.eindopdracht.repositories.OrderRepository;
import nl.backend.eindopdracht.utils.TypeOfWork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
        @Mock
        OrderRepository orderRepository;

        @InjectMocks
        OrderService orderService;

        List<Order> orders = new ArrayList<>();

        @BeforeEach
        void setup() {

            File file = new File();

            Order order1 = new Order();
            order1.setId(1);
            order1.setTypeOfWork(TypeOfWork.HUIS);
            order1.setAmount(20);
            order1.setPrice(300.00);
            order1.setProductName("bleek");
            order1.setStatus("in behandeling");
            order1.setDateCreated(LocalDate.of(2024, 10,10));
            order1.setTime(LocalTime.of(8,0));
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
            order2.setDateCreated(LocalDate.of(2024, 1,10));
            order2.setTime(LocalTime.of(11,0));
            order2.setWorkAddress("Schoonmaakstraat 123");
            order2.setWorkZipcode("2548 DV");
            orders.add(order2);


        }

        @AfterEach
        void tearDown() {

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
        void getOrderById(){
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
        void recordNotFoundException(){
            assertThrows(RecordNotFoundException.class, () -> orderService.getOrderById(9L));
        }

    }
