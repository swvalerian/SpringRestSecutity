package com.swvalerian.springrestapi.security;

import com.swvalerian.springrestapi.model.UserPassw;
import com.swvalerian.springrestapi.repository.UserPasswRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPasswRepository userPasswRepository;

    @Autowired
    public UserDetailsServiceImpl(UserPasswRepository userPasswRepository) {
        this.userPasswRepository = userPasswRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPassw userPassw = userPasswRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User dosn't exist"));
        return SecurityUser.fromUserPassw(userPassw);
    }
}
