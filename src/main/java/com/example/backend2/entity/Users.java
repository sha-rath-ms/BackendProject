package com.example.backend2.entity;

import com.example.backend2.repository.table.UserTable;
import lombok.Data;

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

    public UserTable toUserTable()
    {
        return new UserTable(this.id,this.pin);
    }
}
