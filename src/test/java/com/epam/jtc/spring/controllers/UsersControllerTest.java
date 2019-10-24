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

    private static final String USER_MAPPING = "/userSession";

    private static final String JSON_CONTENT_TYPE =
            "application/json;charset=UTF-8";
    private static final String FORM_DATA_CONTENT_TYPE =
            "multipart/form-data;charset=UTF-8";

    private static final String LOG_IN_METHOD_NAME = "logInUser";
    private static final String LOG_OUT_METHOD_NAME = "logOutUser";

    private static final String USER_LOGIN_PARAM_NAME = "login";
    private static final String USER_LOGIN_PARAM_VALUE =
            "Test " + USER_LOGIN_PARAM_NAME;
    private static final String USER_PASS_PARAM_NAME = "password";
    private static final String USER_PASS_PARAM_VALUE =
            "Test " + USER_PASS_PARAM_NAME;

    private static final String DEFAULT_JSON_PATH = "$";


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
        this.mockMvc.perform(post(USER_MAPPING)
                .contentType(FORM_DATA_CONTENT_TYPE)
                .param(USER_LOGIN_PARAM_NAME, USER_LOGIN_PARAM_VALUE)
                .param(USER_PASS_PARAM_NAME, USER_PASS_PARAM_VALUE)
        ).andDo(print())

                .andExpect(
                        content().contentType(JSON_CONTENT_TYPE))
                .andExpect(handler().methodName(LOG_IN_METHOD_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath(DEFAULT_JSON_PATH).isNotEmpty());
    }


    @Test
    public void logOutUser() throws Exception {
        this.mockMvc.perform(delete(USER_MAPPING)).andDo(print())

                .andExpect(handler().methodName(LOG_OUT_METHOD_NAME))
                .andExpect(status().isOk());
    }
}