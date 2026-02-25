package com.lurdharry.medicationReminder.user;

import com.lurdharry.medicationReminder.user.dto.UserResponse;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    public UserResponse getProfile(User user) {
        return mapper.toUserResponse(user);
    }

}
