package com.example.backend2.entity;

import com.example.backend2.repository.table.UserTable;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@Data
public class Users {
    @NotNull
    private final long id;
    private final int pin;

    public Users(long id, int pin) {
        this.id = id;
        this.pin = pin;
    }

    public UserTable toUserTable(PasswordEncoder passwordEncoder) {
        return new UserTable(this.id, passwordEncoder.encode(Integer.toString(this.pin)));
    }
}
