package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.SpringConfiguration;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpClasses.TestConfigurationUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {SpringConfiguration.class, TestConfigurationUtils.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class BooksControllerTest {

    private static final Logger logger =
            LogManager.getLogger(BooksControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        //this.mockMvc =
        //        MockMvcBuilders.standaloneSetup(BooksController.class)
        //                .setValidator(new BookValidator()).build();

        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllBooks() throws Exception {
        this.mockMvc.perform(get("/books")).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("getAllBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }

    @Test
    public void addBook() throws Exception {
        this.mockMvc.perform(post("/books")
                .contentType("multipart/form-data")
                .characterEncoding("UTF-8")
                .param("title", "Test title")
                .param("author", "Test author")).andDo(print())

                .andExpect(handler().methodName("addBook"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isNotEmpty()
                );

    }

    @Test
    public void updateBook() throws Exception {

        Book bookForUpdate = new Book();
        bookForUpdate.setId(1);
        bookForUpdate.setRating(0);

        String json = new ObjectMapper().writer().withDefaultPrettyPrinter()
                .writeValueAsString(bookForUpdate);


        this.mockMvc.perform(post("/books/{bookId}", 1)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andDo(print())

                .andExpect(handler().methodName("updateBook"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }

    @Test
    public void getBookImage() throws Exception {
        this.mockMvc.perform(get("/books/{bookId}/image", 1)
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())

                .andExpect(handler().methodName("getBookImage"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("image/*")
                );
    }

    @Test
    public void deleteBook() throws Exception {
        this.mockMvc.perform(
                request(HttpMethod.DELETE, "/books/{bookId}", 1))
                .andDo(print())

                .andExpect(handler().methodName("deleteBook"))
                .andExpect(status().isOk());
    }
}