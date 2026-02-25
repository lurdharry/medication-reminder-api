package com.lurdharry.medicationReminder.auth;

import com.lurdharry.medicationReminder.auth.dto.LoginRequest;
import com.lurdharry.medicationReminder.auth.dto.LoginResponse;
import com.lurdharry.medicationReminder.config.JwtUtility;
import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.user.UserMapper;
import com.lurdharry.medicationReminder.user.UserRepository;
import com.lurdharry.medicationReminder.user.dto.UserRequest;
import com.lurdharry.medicationReminder.user.dto.UserResponse;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private  final JwtUtility jwtUtility;
    private final UserMapper mapper;

    public LoginResponse login(@Valid LoginRequest request) {

        User user = userRepository.findByEmail(request.email()).orElseThrow(
                ()-> new ResponseException("User not registered", HttpStatus.NOT_FOUND)
        );
        if (!encoder.matches(request.password(), user.getPasswordHash())){
            throw new ResponseException("Wrong password", HttpStatus.UNAUTHORIZED);
        }

        var accessToken  = jwtUtility.generateToken(user);

        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(accessToken)
                .build();
    };

    public UserResponse createUser(UserRequest userRequest){

        var existingUser = userRepository.findByEmail(userRequest.email());

        if (existingUser.isPresent()) {
            throw new ResponseException("Email already exists", HttpStatus.CONFLICT);
        }

        String passwordHash = encoder.encode(userRequest.password());
        var user =  mapper.toUser(userRequest);
        user.setPasswordHash(passwordHash);

        userRepository.save(user);

        return mapper.toUserResponse(user);
    }
}
