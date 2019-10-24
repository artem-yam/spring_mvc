package com.epam.jtc.spring.controllers;

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
        this.mockMvc.perform(get("/tags")).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("getAllTags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }

    @Test
    public void addNewTag() throws Exception {
        this.mockMvc.perform(post("/tags")
                .contentType("application/json;charset=UTF-8")
                .param("bookId", "1")
                .param("tag", "Test tag")).andDo(print())

                .andExpect(handler().methodName("addNewTag"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }


    @Test
    public void addTagToBook() throws Exception {
        this.mockMvc.perform(post("/tags/{tag}/books", "Test tag")
                .contentType("application/json;charset=UTF-8")
                .content("1")).andDo(print())

                .andExpect(handler().methodName("bindTagToBook"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }

    @Test
    public void unbindTag() throws Exception {
        this.mockMvc.perform(delete("/tags/{tag}/books", "Test tag")
                .contentType("application/json;charset=UTF-8")
                .content("1")).andDo(print())

                .andExpect(handler().methodName("unbindTag"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty()
                );
    }
}