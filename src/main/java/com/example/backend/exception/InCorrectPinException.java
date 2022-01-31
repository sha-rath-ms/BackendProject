package com.example.backend2.exception;

import com.example.backend2.response.ResultInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InCorrectPinException extends RuntimeException {
    private ResultInfo resultInfo;
}
