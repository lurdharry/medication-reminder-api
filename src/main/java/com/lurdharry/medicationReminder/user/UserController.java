package com.lurdharry.medicationReminder.user;

import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/createUser")
    public ResponseEntity<ResponseDTO> createUser(
            @RequestBody @Valid UserRequest request
    ) {

        var user = userService.createUser(request);

        ResponseDTO res = new ResponseDTO(HttpStatus.CREATED.value(), "User Created", user);

        return  new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
