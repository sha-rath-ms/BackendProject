package com.example.backend.repository.table;

import com.example.backend.entity.Sites;
import com.example.backend.sector.Sector;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "sites")
@NoArgsConstructor
public class SiteTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;
    @Column(name = "sitename")
    private String siteName;
    private Sector sector;
    @Column(name = "username")
    private String userName;
    private String password;
    private String notes;
    @Column(name = "userid")
    private long userId;
    @CreationTimestamp
    private Instant created_at;
    @UpdateTimestamp
    private Instant updated_at;

    public SiteTable(String url, String siteName, Sector sector, String userName, String password, String notes) {
        this.url = url;
        this.siteName = siteName;
        this.sector = sector;
        this.userName = userName;
        this.password = password;
        this.notes = notes;
    }

    public Sites toSite() {
        return new Sites(this.id, this.url, this.siteName, this.sector, this.userName, this.password, this.notes);
    }

}
