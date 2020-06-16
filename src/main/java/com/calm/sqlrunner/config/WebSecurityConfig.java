package com.calm.sqlrunner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import java.net.URI;

/**
 * security 相关配置.
 *
 * @author gaozhirong on 2020/02/05
 * @version 1.0.0
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    /**
     * 注册密码加密器.
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 security 过滤器链.
     * @param http http security 实例
     * @return 配置后的过滤器链
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/v2/api-docs", "/actuator/**", "/api/v1/database/actions/**",
                        "/api/v1/sql/actions/**", "/html/login.html", "/logout", "/js/**", "/css/**",
                        "/html/registration.html", "/api/v1/user", "/images/favicon.ico", "/test/**")
                .permitAll()
                .anyExchange().authenticated()
                .and()
                    .httpBasic().and()
                    .formLogin().loginPage("/html/login.html")
                    .requiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/custom/login"))
                    .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler())
                    .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/html/login.html?error"))
                .and()
                    .logout().requiresLogout(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/logout"))
                    .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                    .csrf().disable()
                    .headers().frameOptions().disable()
                .and().build();
    }

    /**
     * 退出登录成功的 handler.
     * @return 生成的 handler
     */
    public ServerLogoutSuccessHandler logoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler serverLogoutSuccessHandler  = new RedirectServerLogoutSuccessHandler();
        serverLogoutSuccessHandler.setLogoutSuccessUrl(URI.create("/html/login.html?logout"));
        return serverLogoutSuccessHandler;
    }

}
