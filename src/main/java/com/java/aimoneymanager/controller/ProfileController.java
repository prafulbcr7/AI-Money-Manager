package com.java.aimoneymanager.controller;

import com.java.aimoneymanager.dto.AuthDTO;
import com.java.aimoneymanager.dto.ProfileDTO;
import com.java.aimoneymanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO registeredProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {
        boolean isActivated = profileService.activateProfile(token);
        if(isActivated) {
            return ResponseEntity.ok("Profile activated Successfully !!!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Activation Token expired or is invalid !!!");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO) {
        try {
            if(!profileService.isUsersAccountActive(authDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                        "message", "Your Account is not Active. Please Active your Account to use it. !!!"
                ));
            }
            Map<String, Object> response = profileService.authenticateUserAndGenerateToken(authDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/test")
    public String testToken() {
        return "Testing ...!!! JWT Token Validation Successfull...";
    }

}
