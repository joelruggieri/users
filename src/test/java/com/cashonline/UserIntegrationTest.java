package com.cashonline;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@Sql({"/test-schema.sql", "/test-data.sql"})
@Transactional
public class UserIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenGetUser_thenStatus200AndUserReturnedProperly() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("Pepe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Is.is("Argento")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("test@app.com.ar")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loans[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loans[0].total", Is.is(2500.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loans[1].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loans[1].total", Is.is(65120.75)));
    }

    @Test
    public void whenCreateUser_thenCreatedIsReturned() throws Exception {
        String user = "{\"first_name\": \"bob\", \"last_name\" : \"dylan\", \"email\" : \"bob@domain.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void whenCreateUserWithoutLastName_thenBadRequestIsReturned() throws Exception {
        String user = "{\"first_name\": \"bob\", \"email\": \"bob@domain.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateUserWithMalformedEmail_thenBadRequestIsReturned() throws Exception {
        String user = "{\"first_name\": \"bob\", \"last_name\": \"dylan\", \"email\": \"domain.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenDeleteUser_thenNoContentIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
