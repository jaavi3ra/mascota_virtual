package com.mascota.mascota_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced //balanceador de cargas IPs disponibles cuando intente llamar a otro microservicio
    //webclient sirve para hacer peticiones http a otras apis
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder(); //devuelve un constructor para personalizar
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            // configuracion para permitir que app externas consuman los endpoint sin que el navegador los bloquee por seguridad
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")            
                        .allowCredentials(false);   //desactiva soporte automatico de credecnciales 
            }
        };
    }
}
