package com.example.microserviceone;

import com.example.microserviceone.auth.AuthenticationResponse;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTests {
    private final MockMvc mockMvc;

    @Autowired
    public TagControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetAllTags() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tags"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddNewTag() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-tag")
                        .contentType("application/json")
                        .content("{\"name\": \"NewTagName\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddNewTagToProduct() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-tag")
                        .contentType("application/json")
                        .content("{\"name\": \"NewProductTagName\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")));

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"TagSellerName\", \"password\": \"123\"}")
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
                        .content("{\"name\": \"TagShopName\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "TagShopName")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"TaggedProductName\", \"description\": \"Description\", \"price\": \"12345\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/product/new-tag/{shopName}/{productName}/{tagName}",
                                "TagShopName", "TaggedProductName", "NewProductTagName")
                        .header("authorization", "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
