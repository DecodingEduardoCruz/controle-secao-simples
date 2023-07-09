package org.inneo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableAutoConfiguration
@ComponentScan(basePackages = "")
public class AuthApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
	
	@Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(AuthApplication.class);
    }

}
