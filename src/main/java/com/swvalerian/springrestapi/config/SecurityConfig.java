package com.swvalerian.springrestapi.config;

import com.swvalerian.springrestapi.model.Permission;
import com.swvalerian.springrestapi.model.Role4Security;
import com.swvalerian.springrestapi.security.JwtConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//       здесь организована авторизация пользователей
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/v1/auth/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);

//                .httpBasic();

                // порядок следования разрешений и запретов - имеет значение! по другому поставить - будет работать по другой логике. Согласно расстановке приоритетов
                // сначало вносятся более узкие приоритеты, потом - расширенные

//                .antMatchers(HttpMethod.POST, "/api/v1/files/**").hasAnyAuthority(Permission.ADMIN_WRITE.getPermission(), Permission.MODER_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/api/v1/files/**").hasAnyRole(Role4Security.MODERATOR.name(), Role4Security.ADMIN.name())
//
//                // запретим юзеру читать данные о юзерах.
//                .antMatchers(HttpMethod.GET, "/api/v1/users/**").not().hasRole(Role4Security.USER.name())
//
//                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role4Security.ADMIN.name(), Role4Security.MODERATOR.name(), Role4Security.USER.name())
//
//                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role4Security.ADMIN.name())
//                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(Role4Security.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role4Security.ADMIN.name())


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    //        настроил пользователей для аутентификации
//    @Bean("userDetailsService")
//    @Override
//    public UserDetailsService userDetailsService() {
//
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                        .username("admin")
//                        .password(passwordEncoder().encode("admin"))
//                        .authorities(Role4Security.ADMIN.getAuthorities())
//                        .build(),
//                User.builder()
//                        .username("moderator")
//                        .password(passwordEncoder().encode("moderator"))
//                        .authorities(Role4Security.MODERATOR.getAuthorities())
//                        .build(),
//                User.builder()
//                        .username("user")
//                        .password(passwordEncoder().encode("user"))
//                        .authorities(Role4Security.USER.getAuthorities())
//                        .build()
//        );

//    }
}
