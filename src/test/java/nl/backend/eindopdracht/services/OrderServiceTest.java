package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.order.OrderOutputDto;
import nl.backend.eindopdracht.models.Order;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        }

        @AfterEach
        void tearDown() {

        }

        @Test
        @DisplayName("Should get all orders")
        void getAllOrders() {

          when(orderRepository.findAll()).thenReturn(orders);
            //Act
            List<OrderOutputDto> result = orderService.getAllOrders();
            //Assert
            assertEquals(orders.size(), result.size());
        }
    }
