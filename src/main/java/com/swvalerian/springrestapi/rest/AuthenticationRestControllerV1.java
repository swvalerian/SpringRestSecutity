package com.swvalerian.springrestapi.rest;

import com.swvalerian.springrestapi.model.UserPassw;
import com.swvalerian.springrestapi.repository.UserPasswRepository;
import com.swvalerian.springrestapi.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private UserPasswRepository userPasswRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserPasswRepository userPasswRepository) {
        this.authenticationManager = authenticationManager;
        this.userPasswRepository = userPasswRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //    аналог login/logout которые прописываем в SecurityConfig при аутентификации с помощью БД
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {

        try {
            String email = requestDTO.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDTO.getPassword()));

            // БУДЕТ ЛИ ЭТО РАБОТАТЬ?
//            UserDetails userDetails = userDetailsService.loadUserByUsername(requestDTO.getEmail());
            UserPassw userPassw = userPasswRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> new UsernameNotFoundException("userPassw doea'not exist"));

            String token = jwtTokenProvider.createToken(requestDTO.getEmail(), userPassw.getRole().name());

            Map<Object, Object> response = new HashMap<>();
            response.put("email", requestDTO.getEmail());
            response.put("token", token);
            return ResponseEntity.ok(response);


        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    //    аналог login/logout которые прописываем в SecurityConfig при аутентификации с помощью БД
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
