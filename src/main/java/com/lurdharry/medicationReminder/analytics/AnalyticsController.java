package com.lurdharry.medicationReminder.analytics;

import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    @GetMapping("/adherence")
    public ResponseEntity<ResponseDTO> getAdherence(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "7") int days
    ) {
        var res = service.getAdherence(user, days);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Adherence retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
