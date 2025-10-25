package com.devops.microservice.controller;

import com.devops.microservice.dto.RequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DevOpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VALID_API_KEY = "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";

    @Test
    void testDevOpsEndpoint_Success() throws Exception {
        RequestDTO request = new RequestDTO("This is a test", "Juan Perez", "Rita Asturia", 45);

        mockMvc.perform(post("/DevOps")
                        .header("X-Parse-REST-API-Key", VALID_API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Juan Perez your message will be sent"))
                .andExpect(header().exists("X-JWT-KWY"));
    }

    @Test
    void testDevOpsEndpoint_MissingApiKey() throws Exception {
        RequestDTO request = new RequestDTO("This is a test", "Juan Perez", "Rita Asturia", 45);

        mockMvc.perform(post("/DevOps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDevOpsEndpoint_InvalidApiKey() throws Exception {
        RequestDTO request = new RequestDTO("This is a test", "Juan Perez", "Rita Asturia", 45);

        mockMvc.perform(post("/DevOps")
                        .header("X-Parse-REST-API-Key", "invalid-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDevOpsEndpoint_GetMethod() throws Exception {
        mockMvc.perform(get("/DevOps")
                        .header("X-Parse-REST-API-Key", VALID_API_KEY))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("ERROR"));
    }

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Healthy"));
    }
}