package org.practice.cartalert.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.practice.cartalert.service.UserService;
import org.practice.cartalert.service.dto.SignupRequest;
import org.practice.cartalert.service.dto.SignupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }
}
