package com.lurdharry.medicationReminder.user;

import com.lurdharry.medicationReminder.user.dto.UserRequest;
import com.lurdharry.medicationReminder.user.dto.UserResponse;
import com.lurdharry.medicationReminder.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserRequest request) {
        return User.builder()
                .email(request.email())
                .name(request.name())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return  UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .build();

    }
}
