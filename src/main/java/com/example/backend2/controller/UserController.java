package com.example.backend2.controller;

import com.example.backend2.entity.Users;
import com.example.backend2.response.ResponseWrapper;
import com.example.backend2.response.ResultInfo;
import com.example.backend2.response.ResultInfoConstants;
import com.example.backend2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper signUp(@RequestBody @Valid Users user) {
        userService.insert(user);
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, null);
    }

    @GetMapping("/forgotPassword")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper<Integer> forgotPassword(@RequestBody long id) {
        return new ResponseWrapper(ResultInfoConstants.SUCCESS, userService.forgotPassword(id));
    }

    @PutMapping("/recoverPassword")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper recoverPassword(@RequestBody @Valid Users users) {
        userService.changePassword(users.getId(), users.getPin());
        return new ResponseWrapper(new ResultInfo("Password is recovered succesfully for " + users.getId()), null);
    }

    @PutMapping("/{id}/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper changePassword(@PathVariable long id, @RequestBody int pin) {
        userService.changePassword(id, pin);
        return new ResponseWrapper(new ResultInfo("Password is reset succesfully for " + id), null);
    }

    @PutMapping("/forgotPassword/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapper otpAuthentication(@PathVariable long id, @RequestBody int otp) {
        userService.otpValidation(id, otp);
        return new ResponseWrapper(new ResultInfo("OTP is Validated"), null);
    }
}
