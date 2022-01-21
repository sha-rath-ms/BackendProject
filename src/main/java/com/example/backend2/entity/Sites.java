package com.example.backend2.entity;

import com.example.backend2.repository.table.SiteTable;
import com.example.backend2.sector.Sector;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Sites {
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

    public SiteTable toSiteTable() {
        return new SiteTable(this.url, this.siteName, this.sector, this.userName, this.password, this.notes);
    }
}
