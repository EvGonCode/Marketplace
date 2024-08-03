package com.example.microserviceone;

import com.example.microserviceone.auth.AuthenticationResponse;
import com.example.microserviceone.domain.User;
import com.example.microserviceone.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTests {

    private final MockMvc mockMvc;

    @Autowired
    public ShopControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetShops() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/shops"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddShopBySeller() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddSellerShopTest\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationResponse authResponse = objectMapper.readValue(responseContent, AuthenticationResponse.class);

        String token = authResponse.getToken();

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-shop")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"SellerShopName\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddShopByAmin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddSellerAdminShopTest\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-shop/{ownerName}", "AddSellerAdminShopTest")
                        .contentType("application/json")
                        .content("{\"name\": \"AdminShop\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddShopByAminToNotSeller() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser")
                        .contentType("application/json")
                        .content("{\"login\": \"AddShopUser\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-shop/{ownerName}", "AddShopUser")
                        .contentType("application/json")
                        .content("{\"name\": \"AdminUserShop\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testAddShopByAminToFakeUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-shop/{ownerName}", "FakeUser")
                        .contentType("application/json")
                        .content("{\"name\": \"AdminFakeUserShop\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
