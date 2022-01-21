package com.example.backend2.service;

import com.example.backend2.entity.Users;
import com.example.backend2.exception.DuplicateKeyException;
import com.example.backend2.exception.InCorrectPinException;
import com.example.backend2.exception.KeyNotFoundException;
import com.example.backend2.exception.ValidationException;
import com.example.backend2.repository.UserRepository;
import com.example.backend2.repository.table.UserTable;
import com.example.backend2.response.ResultInfoConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean insert(Users users) {
        if (userRepository.existsById(users.getId())) {
            log.warn("User is already present with id:{}", users.getId());
            throw new DuplicateKeyException(ResultInfoConstants.DUPLICATE_USER, users.getId());
        }
        if (users.getId() / 1000000000 > 0 && users.getId() / 1000000000 < 10) {
            if (users.getPin() / 1000 > 0 && users.getPin() / 1000 < 10) {
                userRepository.save(users.toUserTable());
            } else {
                log.warn("Pin is not Valid");
                throw new InCorrectPinException(ResultInfoConstants.INVALID_PIN);
            }
        } else {
            log.warn("User mobile number is not valid");
            throw new ValidationException(ResultInfoConstants.NUMBER_VALIDATION);
        }
        return true;
    }

    public boolean login(Users users) {
        if (!userRepository.existsById(users.getId())) {
            log.warn("User not found with id:{}", users.getId());
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        if (userRepository.getById(users.getId()).getPin() != users.getPin()) {
            log.warn("Pin is incorrect and authentication failed");
            throw new InCorrectPinException(ResultInfoConstants.INCORRECT_PIN);
        }
        return true;
    }

    public int forgotPassword(long id) {
        Optional<UserTable> oldUser = userRepository.findById(id);
        if (!oldUser.isPresent()) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        Random random = new Random();
        UserTable newUser = oldUser.get();
        int otp = random.nextInt(9999 - 1000) + 1000;
        newUser.setOtp(otp);
        newUser.setCreated_at(oldUser.get().getCreated_at());
        userRepository.save(oldUser.get());
        return otp;
    }

    public boolean changePassword(long id, int password) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        if (password / 1000 <= 0 || password / 1000 >= 10) {
            log.warn("Pin is not Valid");
            throw new InCorrectPinException(ResultInfoConstants.INVALID_PIN);
        }
        Optional<UserTable> oldUser = userRepository.findById(id);
        UserTable newUser = new UserTable(id, password);
        newUser.setCreated_at(oldUser.get().getCreated_at());
        userRepository.save(newUser);
        return true;
    }

    public boolean otpValidation(long id, int otp) {
        Optional<UserTable> oldUser = userRepository.findById(id);
        if (!oldUser.isPresent()) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        if (oldUser.get().getOtp() != otp) {
            log.warn("Otp is not valid");
            throw new InCorrectPinException(ResultInfoConstants.INVALID_OTP);
        }
        oldUser.get().setOtp(0);
        userRepository.save(oldUser.get());
        return true;
    }
}
