package com.lurdharry.medicationReminder.ai;

import com.lurdharry.medicationReminder.ai.dto.ChatRequest;
import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    @PostMapping("/chat")
    public ResponseEntity<ResponseDTO> chat(
            @RequestBody @Valid ChatRequest request,
            @AuthenticationPrincipal User user
    ) {
        var res = aiService.chat(request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "AI response", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/chat")
    public ResponseEntity<ResponseDTO> getConversation(@AuthenticationPrincipal User user) {
        var res = aiService.getConversation(user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Conversation retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/chat")
    public ResponseEntity<ResponseDTO> clearConversation(@AuthenticationPrincipal User user) {
        aiService.clearConversation(user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Conversation cleared", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

