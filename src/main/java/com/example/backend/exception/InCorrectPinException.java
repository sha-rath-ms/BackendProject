package com.example.backend.exception;

import com.example.backend.response.ResultInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InCorrectPinException extends RuntimeException {
    private ResultInfo resultInfo;
}
