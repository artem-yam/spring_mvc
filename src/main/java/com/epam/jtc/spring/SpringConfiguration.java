package com.epam.jtc.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring configuration
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.jtc.spring")
public class SpringConfiguration implements WebMvcConfigurer {
}
