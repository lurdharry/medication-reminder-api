package com.lurdharry.medicationReminder.user;

import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<ResponseDTO> getProfile (
            @AuthenticationPrincipal User user
    ){
        var response = userService.getProfile(user);
        ResponseDTO res = new ResponseDTO(HttpStatus.OK.value(), "Profile retrieved", response);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
