package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dto.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {TestConfigurationUtils.class})
@WebAppConfiguration
public class NotificationsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wac.getBean(NotificationsController.class))
                .build();
    }


    @Test
    public void getAllNotifications() throws Exception {
        this.mockMvc.perform(get("/notifications")).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("getAllNotifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }


    @Test
    public void addNotification() throws Exception {

        Notification testNotification = new Notification();
        testNotification.setDate(new Date(0));

        String json = new ObjectMapper().writer().withDefaultPrettyPrinter()
                .writeValueAsString(testNotification);

        this.mockMvc.perform(post("/notifications")
                .contentType("application/json;charset=UTF-8")
                .content(json)).andDo(print())

                .andExpect(handler().methodName("addNotification"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isNotEmpty());

    }
}