package com.example.backend.entity;

import com.example.backend.repository.table.SiteTable;
import com.example.backend.sector.Sector;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@Data
public class Sites {
    private final long id;
    @NotNull
    private final String url;
    @NotNull
    private final String siteName;
    @NotNull
    private final Sector sector;
    @NotNull
    private final String userName;
    @NotNull
    private final String password;
    private final String notes;

    public SiteTable toSiteTable(PasswordEncoder passwordEncoder) {
        return new SiteTable(this.url, this.siteName, this.sector, this.userName, passwordEncoder.encode(this.password), this.notes);
    }
}
