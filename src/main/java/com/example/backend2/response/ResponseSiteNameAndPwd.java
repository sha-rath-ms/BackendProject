package com.example.backend2.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResponseSiteNameAndPwd {
    @NotNull
    private final String siteName;
    @NotNull
    private final String password;
}
