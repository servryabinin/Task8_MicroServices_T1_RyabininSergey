package ru.example.micro.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.example.micro.security.config.RestTemplateConfig;
import ru.example.micro.security.config.SecurityConfig;
import ru.example.micro.security.token.ServiceTokenHolder;

@Configuration
@ComponentScan("ru.example.micro.security.util")
@Import({RestTemplateConfig.class, SecurityConfig.class, ServiceTokenHolder.class})
@ConditionalOnMissingBean(SecurityConfig.class)
public class SecurityAutoConfiguration {
}
