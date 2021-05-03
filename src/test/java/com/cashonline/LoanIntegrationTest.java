package com.cashonline;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@Sql({"/test-schema.sql", "/test-data.sql"})
public class LoanIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenListAllLoansInOnePage_thenStatus200AndLoansAreReturnedProperly() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?page=1&size=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.page", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.size", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.total", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].userId", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].total", Is.is(2500.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].userId", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].total", Is.is(65120.75)));
    }

    @Test
    public void whenListLoansForUser2_thenNoItemsAreReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?page=1&size=3&user_id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.page", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.size", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.total", Is.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.hasSize(0)));
    }

    @Test
    public void whenListPage2ForUser1_thenOneLoneIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?page=2&size=1&user_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.page", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.size", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.total", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].userId", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].total", Is.is(65120.75)));
    }

    @Test
    public void whenSizeIsGraterThanTotalAmountOfLoans_thenSecondPageIsEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?page=2&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.page", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.size", Is.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.total", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.hasSize(0)));
    }

    @Test
    public void whenListLoansWithoutSizeQueryParam_thenBadRequestIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?page=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenListLoansWithoutPageQueryParam_thenBadRequestIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenListLoansWithNonPositivePageQueryParam_thenBadRequestIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?size=0&size=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenListLoansWithNonPositiveSizeQueryParam_thenBadRequestIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/loans?size=1&size=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
