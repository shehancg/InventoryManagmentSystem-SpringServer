package com.exe.inventorymsystemserver.Utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add a resource handler for the "inventory" directory
        // Assuming images are stored in C:/Users/SHEHAN/Pictures/inventory/
        registry.addResourceHandler("/inventory/**")
                .addResourceLocations("file:C:/Users/SHEHAN/Pictures/inventory/");
    }
}
