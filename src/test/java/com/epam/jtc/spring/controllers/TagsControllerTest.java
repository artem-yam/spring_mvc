package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.SpringConfiguration;
import helpClasses.TestConfigurationUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.naming.NamingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {SpringConfiguration.class, MockMvcAutoConfiguration.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class TagsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            new TestConfigurationUtils().setUpDataSourceJNDI();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllTags() throws Exception {
        this.mockMvc.perform(get("/tags")).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("getAllTags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public void addTagToBook() throws Exception {
        this.mockMvc.perform(post("/tags/{tag}", "test tag")
                .contentType("application/json;charset=UTF-8")
                .content("1")).andDo(print())

                .andExpect(handler().methodName("addTagToBook"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray());
    }
}