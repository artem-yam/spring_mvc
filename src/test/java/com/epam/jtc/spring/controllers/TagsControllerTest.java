package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dto.Book;
import helpClasses.TestConfigurationUtils;
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
public class TagsControllerTest {

    private static final String TAGS_MAPPING = "/tags";
    private static final String TAGS_BIND_MAPPING =
            TAGS_MAPPING + "/{tag}/books";

    private static final String JSON_CONTENT_TYPE =
            "application/json;charset=UTF-8";

    private static final String DEFAULT_JSON_PATH = "$";

    private static final String GET_TAGS_METHOD_NAME = "getAllTags";
    private static final String ADD_TAG_METHOD_NAME = "addNewTag";
    private static final String BIND_TAG_METHOD_NAME = "bindTagToBook";
    private static final String UNBIND_TAG_METHOD_NAME = "unbindTag";

    private static final String TAG_TEXT_PARAM_NAME = "tag";
    private static final String TAG_TEXT_PARAM_VALUE =
            "Test " + TAG_TEXT_PARAM_NAME;

    private static final String BOOK_ID_PARAM_NAME = "bookId";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wac.getBean(TagsController.class)).build();
    }

    @Test
    public void getAllTags() throws Exception {
        this.mockMvc.perform(get(TAGS_MAPPING)).andDo(print())

                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(handler().methodName(GET_TAGS_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isArray())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty()
                );
    }

    @Test
    public void addNewTag() throws Exception {
        this.mockMvc.perform(post(TAGS_MAPPING)
                .contentType(JSON_CONTENT_TYPE)
                .param(BOOK_ID_PARAM_NAME, String.valueOf(new Book().getId()))
                .param(TAG_TEXT_PARAM_NAME, TAG_TEXT_PARAM_VALUE))
                .andDo(print())

                .andExpect(handler().methodName(ADD_TAG_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isArray())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty()
                );
    }


    @Test
    public void addTagToBook() throws Exception {
        this.mockMvc.perform(post(TAGS_BIND_MAPPING, TAG_TEXT_PARAM_VALUE)
                .contentType(JSON_CONTENT_TYPE)
                .content(String.valueOf(new Book().getId()))).andDo(print())

                .andExpect(handler().methodName(BIND_TAG_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isArray())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty()
                );
    }

    @Test
    public void unbindTag() throws Exception {
        this.mockMvc.perform(delete(TAGS_BIND_MAPPING, TAG_TEXT_PARAM_VALUE)
                .contentType(JSON_CONTENT_TYPE)
                .content(String.valueOf(new Book().getId()))).andDo(print())

                .andExpect(handler().methodName(UNBIND_TAG_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isArray())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isEmpty()
                );
    }
}