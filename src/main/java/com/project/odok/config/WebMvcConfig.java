package com.project.odok.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        "http://hot6-front.s3-website.ap-northeast-2.amazonaws.com/",
                        "https://www.o-dok.com",
                        "https://o-dok.com")
                .allowedMethods("GET","POST","PUT","DELETE")
                .exposedHeaders("Authorization","Refresh-Token")
                .allowCredentials(true)
        ;
    }
}
