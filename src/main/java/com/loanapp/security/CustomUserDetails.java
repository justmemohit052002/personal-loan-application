package com.loanapp.security;

import com.loanapp.entity.User;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // ✅ Provide role with ROLE_ prefix (VERY IMPORTANT)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    // ✅ Return encrypted password
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // ✅ Username = email
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // ✅ Account status checks (can customize later)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    // 🔥 You can connect this with isDeleted if you want
    @Override
    public boolean isEnabled() {
        return !Boolean.TRUE.equals(user.getIsDeleted());
    }

    // ✅ Custom method to access full User
    public User getUser() {
        return user;
    }
}