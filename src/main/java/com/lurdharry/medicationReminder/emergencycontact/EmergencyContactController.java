package com.lurdharry.medicationReminder.emergencycontact;

import com.lurdharry.medicationReminder.emergencycontact.dto.EmergencyContactRequest;
import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emergency-contacts")
public class EmergencyContactController {

    private final EmergencyContactService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> addContact(
            @RequestBody @Valid EmergencyContactRequest request,
            @AuthenticationPrincipal User user
    ) {
        var res = service.addContact(request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Contact added", res);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getContacts(@AuthenticationPrincipal User user) {
        var res = service.getContacts(user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Contacts retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateContact(
            @PathVariable UUID id,
            @RequestBody @Valid EmergencyContactRequest request,
            @AuthenticationPrincipal User user
    ) {
        var res = service.updateContact(id, request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Contact updated", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteContact(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        service.deleteContact(id, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Contact deleted", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

