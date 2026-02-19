package com.lurdharry.medicationReminder.user;

import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.user.dto.UserResponse;
import com.lurdharry.medicationReminder.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    public UserResponse createUser(UserRequest userRequest){

        var existingUser = userRepository.findByEmail(userRequest.email());

        if (existingUser.isPresent()) {
            throw new ResponseException("Email already exists", HttpStatus.CONFLICT);
        }

        String passwordHash = passwordEncoder.encode(userRequest.password());
        var user =  mapper.toUser(userRequest);
        user.setPasswordHash(passwordHash);

        userRepository.save(user);

        return mapper.toUserResponse(user);
    }
}
