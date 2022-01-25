package com.example.backend2.security;

import com.example.backend2.exception.ValidationException;
import com.example.backend2.repository.UserRepository;
import com.example.backend2.repository.table.UserTable;
import com.example.backend2.response.ResultInfoConstants;
import com.example.backend2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserTable> users = userRepository.findById(Long.parseLong(username));
        if (!users.isPresent()) {
            log.warn("User is not found");
            throw new ValidationException(ResultInfoConstants.INVALID_USER);
        }
        return users.map(MyUserDetails::new).get();
    }
}
