package com.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:src\\main\\resources\\image\\");
        registry.addResourceHandler("/dump/**")
                .addResourceLocations("file:dump\\");
        registry.addResourceHandler("/data/**")
                .addResourceLocations("file:src\\main\\resources\\data\\");
    }
}
