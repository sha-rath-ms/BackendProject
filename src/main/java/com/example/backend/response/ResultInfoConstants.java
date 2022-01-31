package com.example.backend2.response;

import lombok.Data;

@Data
public class ResultInfoConstants {
    public static final ResultInfo DUPLICATE_USER = new ResultInfo("User is already present");
    public static final ResultInfo INCORRECT_PIN = new ResultInfo("Incorrect pin and authenticaton failed");
    public static final ResultInfo NUMBER_VALIDATION = new ResultInfo("Number is not valid");
    public static final ResultInfo INVALID_PIN = new ResultInfo("Pin is not valid");
    public static final ResultInfo INVALID_USER = new ResultInfo("User is not valid");
    public static final ResultInfo INVALID_OTP = new ResultInfo("OTP is not valid");
    public static final ResultInfo SITE_NAME_NOT_FOUND = new ResultInfo("Site name not found");
    public static final ResultInfo SUCCESS = new ResultInfo("Success");
    public static final ResultInfo OTP_SENT = new ResultInfo("OTP is sent to the mobile number");
    public static final ResultInfo INVALID_SITE_ID = new ResultInfo("site id is not present in this user id");
}
