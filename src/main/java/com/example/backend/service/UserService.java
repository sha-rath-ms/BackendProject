package com.example.backend.service;

import com.example.backend.entity.Users;
import com.example.backend.exception.DuplicateKeyException;
import com.example.backend.exception.InCorrectPinException;
import com.example.backend.exception.KeyNotFoundException;
import com.example.backend.exception.ValidationException;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.table.UserTable;
import com.example.backend.response.ResultInfoConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.backend.extract.GenerateOtp.getOtp;

@Service
@Slf4j
@Data
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void insert(Users users) {
        if (userRepository.existsById(users.getId())) {
            log.warn("User is already present with id:{}", users.getId());
            throw new DuplicateKeyException(ResultInfoConstants.DUPLICATE_USER, users.getId());
        }
        if (users.getId() / 1000000000 > 0 && users.getId() / 1000000000 < 10) {
            if (users.getPin() / 1000 > 0 && users.getPin() / 1000 < 10) {
                userRepository.save(users.toUserTable(passwordEncoder));
            } else {
                log.warn("Pin is not Valid");
                throw new InCorrectPinException(ResultInfoConstants.INVALID_PIN);
            }
        } else {
            log.warn("User mobile number is not valid");
            throw new ValidationException(ResultInfoConstants.NUMBER_VALIDATION);
        }
    }

    public int forgotPassword(long id) {
        Optional<UserTable> oldUser = userRepository.findById(id);
        if (!oldUser.isPresent()) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        UserTable newUser = oldUser.get();
        int otp = getOtp();
        newUser.setOtp(otp);
        newUser.setCreated_at(oldUser.get().getCreated_at());
        userRepository.save(oldUser.get());
        return otp;
    }

    public void changePassword(long id, int password) {
        Optional<UserTable> oldUser = userRepository.findById(id);
        if (!oldUser.isPresent()) {
            log.warn("User not found with id:{}", id);
            throw new KeyNotFoundException(ResultInfoConstants.INVALID_USER);
        }
        if (password / 1000 <= 0 || password / 1000 >= 10) {
            log.warn("Pin is not Valid");
            throw new InCorrectPinException(ResultInfoConstants.INVALID_PIN);
        }
        UserTable newUser = new Users(id, password).toUserTable(passwordEncoder);
        newUser.setCreated_at(oldUser.get().getCreated_at());
        userRepository.save(newUser);
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
