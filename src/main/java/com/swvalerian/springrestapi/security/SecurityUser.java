package com.swvalerian.springrestapi.security;

import com.swvalerian.springrestapi.model.UserPassw;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActiv;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActiv) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActiv = isActiv;
    }

    public static UserDetails fromUserPassw(UserPassw userPassw) {
        return new User(
            userPassw.getEmail(), userPassw.getPassword(),
                userPassw.getRole().getAuthorities()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActiv;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActiv;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActiv;
    }

    @Override
    public boolean isEnabled() {
        return isActiv;
    }
}
