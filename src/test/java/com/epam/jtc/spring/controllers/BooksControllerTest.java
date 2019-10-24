package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.BookValidator;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpClasses.TestConfigurationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        classes = {TestConfigurationUtils.class})
@WebAppConfiguration
public class BooksControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wac.getBean(BooksController.class))
                .setValidator(new BookValidator()).build();
    }

    @Test
    public void getBooks() throws Exception {
        this.mockMvc.perform(get("/books")).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("getBooks"))
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


        this.mockMvc.perform(put("/books/{bookId}", 1)
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
        this.mockMvc.perform(request(HttpMethod.DELETE, "/books")
                .contentType("application/json;charset=UTF-8")
                .content("1")).andDo(print())

                .andExpect(handler().methodName("deleteBook"))
                .andExpect(status().isOk());
    }
}