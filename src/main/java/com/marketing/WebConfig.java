package com.marketing;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer{
	

    @PostConstruct
    public void init() {
        System.out.println("🔥 WebConfig cargado 🔥");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/FileGral/**")
                .addResourceLocations("file:///home/sw/FileGral/");

        System.out.println(" ResourceHandler registrado");
    }

}
