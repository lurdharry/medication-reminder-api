package com.lurdharry.medicationReminder.auth;

import com.lurdharry.medicationReminder.auth.dto.LoginRequest;
import com.lurdharry.medicationReminder.auth.dto.LoginResponse;
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
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(
            @RequestBody @Valid LoginRequest request
            ){

        LoginResponse response = authService.login(request);

        ResponseDTO res = new ResponseDTO(HttpStatus.OK.value(), "Login Successful", response);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> createUser(
            @RequestBody @Valid UserRequest request
    ) {

        var user = authService.createUser(request);

        ResponseDTO res = new ResponseDTO(HttpStatus.CREATED.value(), "User Created", user);

        return  new ResponseEntity<>(res, HttpStatus.CREATED);

    }

}
