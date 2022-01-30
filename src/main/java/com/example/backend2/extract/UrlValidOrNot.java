package com.example.backend2.extract;

import com.example.backend2.exception.ValidationException;
import com.example.backend2.response.ResultInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class UrlValidOrNot  {
    public static boolean isUrlValid(String siteUrl) throws IOException {
        try {
            URL url = new URL(siteUrl.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true;
            }
        } catch (Exception e) {
            log.warn("Invalid URL");
            throw new ValidationException(new ResultInfo(e.getMessage()));
        }
        return false;
    }
}
