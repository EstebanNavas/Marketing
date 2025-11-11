package com.marketing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer{
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Para Windows
        registry.addResourceHandler("/FileGral/**")
                .addResourceLocations("file:/C:/proyectoWeb/FileGral/");

        // Linux
        // .addResourceLocations("file:/proyectoWeb/FileGral/");
    }

}
