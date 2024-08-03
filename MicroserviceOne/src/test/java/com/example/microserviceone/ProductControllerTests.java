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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {
    private final MockMvc mockMvc;

    @Autowired
    public ProductControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetProducts() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/products"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddProductToShop() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddProductSeller\", \"password\": \"123\"}")
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
                        .content("{\"name\": \"ShopWithProducts\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "ShopWithProducts")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"ProductInShopName\", \"description\": \"Description\", \"price\": \"12345\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddProductToFakeShop() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddProductFakeSeller\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationResponse authResponse = objectMapper.readValue(responseContent, AuthenticationResponse.class);
        String token = authResponse.getToken();

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "FakeShop")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"ProductInFakeShopName\", \"description\": \"Description\", \"price\": \"12345\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testAddProductToWrongShop() throws Exception {
        MvcResult TrueSellerResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddProductRightSeller\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andReturn();

        String trueResponseContent = TrueSellerResult.getResponse().getContentAsString();
        ObjectMapper trueObjectMapper = new ObjectMapper();
        AuthenticationResponse TrueAuthResponse = trueObjectMapper.readValue(trueResponseContent, AuthenticationResponse.class);
        String trueSellerToken = TrueAuthResponse.getToken();

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-shop")
                        .header("authorization", "Bearer " + trueSellerToken)
                        .contentType("application/json")
                        .content("{\"name\": \"TrueSellerShop\"}"));


        MvcResult fakeSellerResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddProductWrongSeller\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andReturn();

        String fakeResponseContent = fakeSellerResult.getResponse().getContentAsString();
        ObjectMapper fakeObjectMapper = new ObjectMapper();
        AuthenticationResponse fakeAuthResponse = fakeObjectMapper.readValue(fakeResponseContent, AuthenticationResponse.class);
        String fakeSellerToken = fakeAuthResponse.getToken();

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "TrueSellerShop")
                        .header("authorization", "Bearer " + fakeSellerToken)
                        .contentType("application/json")
                        .content("{\"name\": \"ProductInFakeShopName\", \"description\": \"Description\", \"price\": \"12345\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testAddFakeTagToProduct() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"AddFakeTagSeller\", \"password\": \"123\"}")
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
                        .content("{\"name\": \"ShopWithFakeTaggedProduct\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "ShopWithFakeTaggedProduct")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"FakeTaggedProduct\", \"description\": \"Description\", \"price\": \"12345\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc
                .perform(MockMvcRequestBuilders.post("/product/new-tag/{shopName}/{productName}/{tagName}",
                                "ShopWithFakeTaggedProduct", "FakeTaggedProduct", "FakeTag")
                        .header("authorization", "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
