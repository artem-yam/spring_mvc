package com.epam.jtc.spring.controllers;

import helpClasses.TestConfigurationUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {TestConfigurationUtils.class})
@WebAppConfiguration
public class UsersControllerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wac.getBean(UsersController.class)).build();
    }

    @Test
    public void logInUser() throws Exception {
        this.mockMvc.perform(post("/userSession").characterEncoding("UTF-8")
                .contentType("multipart/form-data")
                .param("login", "Test login")
                .param("password", "Test password")
        ).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("logInUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }


    @Test
    public void logOutUser() throws Exception {
        this.mockMvc.perform(delete("/userSession")).andDo(print())

                .andExpect(handler().methodName("logOutUser"))
                .andExpect(status().isOk());
    }
}