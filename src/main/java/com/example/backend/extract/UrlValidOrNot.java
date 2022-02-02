package com.example.backend.extract;

import com.example.backend.exception.ValidationException;
import com.example.backend.response.ResultInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class UrlValidOrNot {
    public static boolean isUrlValid(String siteUrl) throws IOException {
        try {
            URL url = new URL(siteUrl.toString());
            return true;
        } catch (Exception e) {
            log.warn("Invalid URL");
            throw new ValidationException(new ResultInfo(e.getMessage()));
        }
    }
}
