package com.swvalerian.springrestapi.config;

import com.swvalerian.springrestapi.model.Role4Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//       здесь организована авторизация пользователей
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()

                // порядок следования разрешений и запретов - имеет значение! по другому поставить - будет работать по другой логике. Согласно расстановке приоритетов
                // сначало вносятся более узкие приоритеты, потом - расширенные

                .antMatchers(HttpMethod.POST, "/api/v1/files/**").hasAnyRole(Role4Security.MODERATOR.name(), Role4Security.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/v1/files/**").hasAnyRole(Role4Security.MODERATOR.name(), Role4Security.ADMIN.name())

                // запретим юзеру читать данные о юзерах.
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").not().hasRole(Role4Security.USER.name())

                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role4Security.ADMIN.name(), Role4Security.MODERATOR.name(), Role4Security.USER.name())

                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role4Security.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(Role4Security.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role4Security.ADMIN.name())

                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
//        настроил пользователей для аутентификации
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles(Role4Security.ADMIN.name())
                        .build(),
                User.builder()
                        .username("moderator")
                        .password(passwordEncoder().encode("moderator"))
                        .roles(Role4Security.MODERATOR.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .roles(Role4Security.USER.name())
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
