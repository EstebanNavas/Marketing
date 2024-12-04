package com.marketing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class imgSiteConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {	
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String pathImageWindows = "file:///C:/proyectoWeb/FileGral/aquamovil/sitioweb/";
		String pathImageLinux = "file:////home/sw/FileGral/aquamovil/sitioweb/";
		
		
		registry.addResourceHandler("/images/**") // URL base
        .addResourceLocations(pathImageLinux);

	}

	
}
