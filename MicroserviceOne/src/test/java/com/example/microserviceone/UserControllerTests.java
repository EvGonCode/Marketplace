package com.example.microserviceone;

import com.example.microserviceone.auth.AuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testAdminAccessToUsers() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users").with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"RegisterTest\", \"password\": \"123\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"AuthUserTest\", \"password\": \"123\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/authenticate").contentType("application/json")
                        .content("{\"login\": \"AuthUserTest\", \"password\": \"123\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUnAuthenticateUser() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"UnAuthUserTest\", \"password\": \"123\"}"))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationResponse authResponse = objectMapper.readValue(responseContent, AuthenticationResponse.class);

        String token = authResponse.getToken();
        mockMvc
                .perform(MockMvcRequestBuilders.post("/unauthenticate").contentType("application/json")
                        .header("authorization", "Bearer " + token)
                        .content(String.format("{\"jwt\": \"%s\"}", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRegisterDuplicateUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"RegisterDuplicate\", \"password\": \"123\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"RegisterDuplicate\", \"password\": \"123\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User RegisterDuplicate already exist"));
    }

}
