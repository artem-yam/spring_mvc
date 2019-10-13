package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.SpringConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ContextConfiguration(classes = {SpringConfiguration.class})
@WebAppConfiguration
public class MainControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc =
                MockMvcBuilders.standaloneSetup(MainController.class).build();
    }

    @Test
    public void init() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(model().size(2))
                .andExpect(view().name("lib"));
    }
}