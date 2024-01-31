package nl.backend.eindopdracht.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.backend.eindopdracht.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

@SpringBootTest
@ActiveProfiles("integration_test")
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void createOrder() throws Exception {
        String jsonInput = """
                {
                 "typeOfWork" : "HUIS",
                 "amount" : 4,
                 "price" : 150.00,
                 "productName" : "WC-ROL",
                 "status" : "In behandeling",
                 "dateCreated" : "02-12-2024",
                 "time" : "12:30",
                 "workAddress" : "Scheldestraat 12",
                 "workZipcode" : "1004 BV"
                 }
                                 
                 """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String createdId = jsonNode.get("id").asText();

        String locationHeader = result.getResponse().getHeader("Location");


        assertThat(locationHeader, matchesPattern("^http://localhost/order/" + createdId ));
    }
}