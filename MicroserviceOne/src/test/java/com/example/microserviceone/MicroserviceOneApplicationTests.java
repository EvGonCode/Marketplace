package com.example.microserviceone;

import com.example.microserviceone.auth.AuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
class MicroserviceOneApplicationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final MockMvc mockMvc;

    @Autowired
    public MicroserviceOneApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("productController"));
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
                        .content("{\"login\": \"Username\", \"password\": \"123\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testAuthenticateUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"Username\", \"password\": \"123\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/authenticate").contentType("application/json")
                        .content("{\"login\": \"Username\", \"password\": \"123\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testUnAuthenticateUser() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerUser").contentType("application/json")
                        .content("{\"login\": \"Username\", \"password\": \"123\"}"))
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
                        .content("{\"login\": \"SellerName\", \"password\": \"123\"}")
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
                        .content("{\"name\": \"ShopName\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddShopByAmin() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"SellerName\", \"password\": \"123\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-shop/{owner_id}", "1")
                        .contentType("application/json")
                        .content("{\"name\": \"ShopName\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
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
                        .content("{\"login\": \"SellerName\", \"password\": \"123\"}")
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
                        .content("{\"name\": \"ShopName\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "ShopName")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"ProductName\", \"description\": \"Description\", \"price\": \"12345\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
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
                        .content("{\"name\": \"TagName\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddNewTagToProduct() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-tag")
                        .contentType("application/json")
                        .content("{\"name\": \"TagName\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")));

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/registerSeller")
                        .contentType("application/json")
                        .content("{\"login\": \"SellerName\", \"password\": \"123\"}")
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
                        .content("{\"name\": \"ShopName\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-product/{shopName}", "ShopName")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"name\": \"ProductName\", \"description\": \"Description\", \"price\": \"12345\"}"));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/product/new-tag")
                        .header("authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content("{\"tagId\": \"1\", \"productId\": \"1\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTagById() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/new-tag")
                        .contentType("application/json")
                        .content("{\"name\": \"TagName\"}")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")));
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tags/{id}", "1")
                        .with(user("admin").password("pass").roles("USER", "ADMIN")))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
