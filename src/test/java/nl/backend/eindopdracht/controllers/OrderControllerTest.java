package nl.backend.eindopdracht.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.backend.eindopdracht.dtos.order.OrderInputDto;
import nl.backend.eindopdracht.dtos.order.OrderOutputDto;
import nl.backend.eindopdracht.models.*;
import nl.backend.eindopdracht.repositories.OrderRepository;
import nl.backend.eindopdracht.services.OrderService;
import nl.backend.eindopdracht.utils.TypeOfWork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@ActiveProfiles("integration_test")
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    private OrderOutputDto orderDto;

    private OrderInputDto orderInputDto;

    @BeforeEach
    void setup() {
        orderDto = new OrderOutputDto();
        orderDto.setId(1L);
        orderDto.setTypeOfWork(TypeOfWork.HUIS);
        orderDto.setAmount(2);
        orderDto.setProductName("WC-ROL");
        orderDto.setStatus("In behandeling");
        orderDto.setDateCreated(LocalDate.of(2024, 5, 5));
        orderDto.setTime(LocalTime.of(15, 15));
        orderDto.setWorkAddress("Maasstraat 12");
        orderDto.setWorkZipcode("1004 BV");

        orderInputDto = new OrderInputDto();
        orderInputDto.setTypeOfWork(TypeOfWork.HUIS);
        orderInputDto.setAmount(2);
        orderInputDto.setProductName("WC-ROL");
        orderInputDto.setStatus("In behandeling");
        orderInputDto.setDateCreated(LocalDate.of(2024, 5, 5));
        orderInputDto.setTime(LocalTime.of(15, 15));
        orderInputDto.setWorkAddress("Maasstraat 12");
        orderInputDto.setWorkZipcode("1004 BV");

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getOrderById() throws Exception {
        Long orderId = 1L;
        orderDto.setId(orderId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = orderDto.getDateCreated().format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = orderDto.getTime().format(timeFormatter);

        when(orderService.getOrderById(orderId)).thenReturn(orderDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", orderId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.typeOfWork").value(orderDto.getTypeOfWork().toString()))
                .andExpect(jsonPath("$.amount").value(orderDto.getAmount()))
                .andExpect(jsonPath("$.productName").value(orderDto.getProductName()))
                .andExpect(jsonPath("$.status").value(orderDto.getStatus()))
                .andExpect(jsonPath("$.dateCreated").value(formattedDate))
                .andExpect(jsonPath("$.time").value(formattedTime))
                .andExpect(jsonPath("$.workAddress").value(orderDto.getWorkAddress()))
                .andExpect(jsonPath("$.workZipcode").value(orderDto.getWorkZipcode()));
    }


    @Test
    void createOrder() throws Exception {
        Long orderId = 1L;
        orderDto.setId(orderId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = orderDto.getDateCreated().format(formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = orderDto.getTime().format(timeFormatter);

        String jsonInput = objectMapper.writeValueAsString(orderInputDto);

        when(orderService.createOrder(any(OrderInputDto.class))).thenReturn(orderDto);


        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.typeOfWork").value(orderDto.getTypeOfWork().toString()))
                .andExpect(jsonPath("$.amount").value(orderDto.getAmount()))
                .andExpect(jsonPath("$.productName").value(orderDto.getProductName()))
                .andExpect(jsonPath("$.status").value(orderDto.getStatus()))
                .andExpect(jsonPath("$.dateCreated").value(formattedDate))
                .andExpect(jsonPath("$.time").value(formattedTime))
                .andExpect(jsonPath("$.workAddress").value(orderDto.getWorkAddress()))
                .andExpect(jsonPath("$.workZipcode").value(orderDto.getWorkZipcode()))
                .andReturn();
    }


}

