package com.example.backend.response;

import lombok.Data;

@Data
public class ResponseWrapper<T> {
    private final ResultInfo resultInfo;
    private final T data;
}
