package com.sample.google_login.controller;

import com.sample.google_login.dto.AuthDto;
import com.sample.google_login.dto.UserDto;
import com.sample.google_login.service.UserService;
import lombok.AllArgsConstructor;
import model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<AuthDto> createUser(@RequestBody UserModel userModel){
        String token = userService.createUser(userModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthDto(token));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        UserDto userDto = userService.fetchUserProfile(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }
}
