package com.example.backend.repository.table;

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
    private String pin;
    private int otp;
    @CreationTimestamp
    private Instant created_at;
    @UpdateTimestamp
    private Instant updated_at;

    public UserTable(long id, String pin) {
        this.id = id;
        this.pin = pin;
    }

}
