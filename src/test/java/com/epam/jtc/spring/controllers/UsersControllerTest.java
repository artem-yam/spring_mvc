package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.SpringConfiguration;
import helpClasses.TestConfigurationUtils;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {SpringConfiguration.class, TestConfigurationUtils.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void logInUser() throws Exception {
        this.mockMvc.perform(post("/users/login")
                .contentType("multipart/form-data").characterEncoding("UTF-8")
                .param("login", "Test login")
                .param("password", "Test password")).andDo(print())

                .andExpect(
                        content().contentType("application/json;charset=UTF-8"))
                .andExpect(handler().methodName("logInUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }


    @Test
    public void logOutUser() throws Exception {
        this.mockMvc.perform(post("/users/logout")).andDo(print())

                .andExpect(handler().methodName("logOutUser"))
                .andExpect(status().isOk());
    }
}