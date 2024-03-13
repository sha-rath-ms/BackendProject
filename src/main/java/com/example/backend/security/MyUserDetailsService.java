package com.example.backend.security;

import com.example.backend.exception.ValidationException;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.table.UserTable;
import com.example.backend.response.ResultInfoConstants;
import com.example.backend.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Data
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserService userService;

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
