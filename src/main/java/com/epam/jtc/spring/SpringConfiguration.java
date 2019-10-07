package com.epam.jtc.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring configuration
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.epam.jtc.spring"})
@Lazy(false)
public class SpringConfiguration implements WebMvcConfigurer {
}
