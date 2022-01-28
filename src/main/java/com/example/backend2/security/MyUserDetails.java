package com.example.backend2.security;

import com.example.backend2.entity.Users;
import com.example.backend2.repository.table.UserTable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
//    PasswordEncoder passwordEncoder;


    public MyUserDetails(UserTable userTable) {
        this.userName = Long.toString(userTable.getId());
        this.password = userTable.getPin();
    }

    public UserTable toUserTable()
    {
        return new UserTable(Long.parseLong(userName),password);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
