package com.br.aweb.sistema_vendas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return http
                // Regras das mais específicas para as mais genéricas
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll();
                    // Cadastros de produtos e clientes: somente ADMIN
                    req.requestMatchers("/produtos/**", "/clientes/**").hasRole("ADMIN");
                    // Pedidos: ADMIN e PADRAO
                    req.requestMatchers("/pedidos/**").hasAnyRole("ADMIN", "PADRAO");
                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/acesso-negado"))
                .build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }

}
