package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.BookValidator;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpClasses.TestConfigurationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {TestConfigurationUtils.class})
@WebAppConfiguration
public class BooksControllerTest {

    private static final String GET_AND_ADD_MAPPING = "/books";
    private static final String UPDATE_MAPPING =
            GET_AND_ADD_MAPPING + "/{bookId}";
    private static final String GET_IMAGE_MAPPING =
            UPDATE_MAPPING + "/image";

    private static final String JSON_CONTENT_TYPE =
            "application/json;charset=UTF-8";
    private static final String FORM_DATA_CONTENT_TYPE =
            "multipart/form-data;charset=UTF-8";
    private static final String IMAGE_CONTENT_TYPE =
            "image/*";

    private static final String DEFAULT_JSON_PATH = "$";

    private static final String GET_BOOKS_METHOD_NAME = "getBooks";
    private static final String ADD_BOOK_METHOD_NAME = "addBook";
    private static final String UPDATE_BOOK_METHOD_NAME = "updateBook";
    private static final String GET_BOOK_IMAGE_METHOD_NAME = "getBookImage";
    private static final String DELETE_BOOK_METHOD_NAME = "deleteBook";

    private static final String ADD_BOOK_TITLE_PARAM_NAME = "title";
    private static final String ADD_BOOK_TITLE_PARAM_VALUE =
            "Test " + ADD_BOOK_TITLE_PARAM_NAME;

    private static final String ADD_BOOK_AUTHOR_PARAM_NAME = "author";
    private static final String ADD_BOOK_AUTHOR_PARAM_VALUE =
            "Test " + ADD_BOOK_AUTHOR_PARAM_NAME;


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
        this.mockMvc.perform(get(GET_AND_ADD_MAPPING)).andDo(print())

                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(handler().methodName(GET_BOOKS_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isArray())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty()
                );
    }

    @Test
    public void addBook() throws Exception {
        this.mockMvc.perform(post(GET_AND_ADD_MAPPING)
                .contentType(FORM_DATA_CONTENT_TYPE)
                .param(ADD_BOOK_TITLE_PARAM_NAME, ADD_BOOK_TITLE_PARAM_VALUE)
                .param(ADD_BOOK_AUTHOR_PARAM_NAME, ADD_BOOK_AUTHOR_PARAM_VALUE))
                .andDo(print())

                .andExpect(handler().methodName(ADD_BOOK_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty()
                );

    }

    @Test
    public void updateBook() throws Exception {

        Book bookForUpdate = new Book();

        String json = new ObjectMapper().writer().withDefaultPrettyPrinter()
                .writeValueAsString(bookForUpdate);


        this.mockMvc.perform(put(UPDATE_MAPPING, bookForUpdate.getId())
                .contentType(JSON_CONTENT_TYPE)
                .content(json))
                .andDo(print())

                .andExpect(handler().methodName(UPDATE_BOOK_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty()
                );
    }

    @Test
    public void getBookImage() throws Exception {
        this.mockMvc.perform(get(GET_IMAGE_MAPPING, new Book().getId())
                .contentType(JSON_CONTENT_TYPE))
                .andDo(print())

                .andExpect(handler().methodName(GET_BOOK_IMAGE_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(IMAGE_CONTENT_TYPE)
                );
    }

    @Test
    public void deleteBook() throws Exception {
        this.mockMvc.perform(delete(GET_AND_ADD_MAPPING)
                .contentType(JSON_CONTENT_TYPE)
                .content(String.valueOf(new Book().getId()))).andDo(print())

                .andExpect(handler().methodName(DELETE_BOOK_METHOD_NAME))
                .andExpect(status().isOk());
    }
}