package com.chat.test.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ChatUserDetails implements UserDetails{

    private final String ROLE_PREFIX = "ROLE_";

    private String username;
    private String role = "user";

    public ChatUserDetails (String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {       
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        return list;
    }

    @Override
    public String getPassword() {     
        return null;
    }

    @Override
    public String getUsername() {     
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {    
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {     
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {      
        return true;
    }

    @Override
    public boolean isEnabled() {     
        return true;
    }
    
}
