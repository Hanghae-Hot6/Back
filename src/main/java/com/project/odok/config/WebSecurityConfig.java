package com.project.odok.config;


import com.project.odok.security.exception.JwtAccessDeniedHandler;
import com.project.odok.security.jwt.JwtAuthenticationEntryPoint;
import com.project.odok.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/members/**").permitAll()
                .antMatchers("/clubs/top5").permitAll()
                .antMatchers("/clubs/search").permitAll()
                .antMatchers("/clubs/reviews").permitAll()
                .antMatchers("/mail/findId").permitAll()
                .antMatchers("/mail/findPassword").permitAll()
                .antMatchers("/mypage/**").authenticated()
                .antMatchers("/clubs/{club-id}/**").authenticated()
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .anyRequest().permitAll()

                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login")

                .and().apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}