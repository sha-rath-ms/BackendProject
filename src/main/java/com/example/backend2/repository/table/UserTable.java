package com.example.backend2.repository.table;

import com.example.backend2.entity.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserTable {
    @Id
    private long id;
    private int pin;
    private int otp;
    @CreationTimestamp
    private Instant created_at;
    @UpdateTimestamp
    private Instant updated_at;

    public UserTable(long id, int pin) {
        this.id = id;
        this.pin = pin;
    }

    public Users toUser() {
        return new Users(this.id, this.pin);
    }
}
