package io.github.tuyendev.auth.common.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class DefaultWebSecurityConfigurer {
    private static final String[] WHITE_LIST_URL = new String[]{"/login",
            "/register",
            "/forgot-password",
            "/css/**",
            "/js/**",
            "/images/**",
            "/webjars/**",
            "/favicon.ico",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"};
    private final String rememberMeKey;

    public DefaultWebSecurityConfigurer(@Value("${app.default.remember-me.key}") String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(req -> req.antMatchers(WHITE_LIST_URL).permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .formLogin(config -> config.loginPage("/login").defaultSuccessUrl("/"))
                .logout().deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and()
                .rememberMe().key(rememberMeKey).tokenValiditySeconds(86400);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/static/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/assets/**")
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js**")
                .antMatchers("/admin/**")
                .antMatchers("/webjars/**")
                .antMatchers("/templates/**");
    }
}
