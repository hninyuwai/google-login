package com.sample.google_login.controller;

import com.sample.google_login.dto.AuthDto;
import com.sample.google_login.dto.UserDto;
import com.sample.google_login.service.UserService;
import lombok.AllArgsConstructor;
import model.EmailModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/login",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class LoginController {

    private UserService userService;

    @PostMapping("/google")
    public ResponseEntity<AuthDto> loginWithGoogle(@RequestBody Map<String, String> body) {
        String access_token = userService.createGoogleUser(body.get("idToken"));

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(new AuthDto(access_token));

    }

    @PostMapping("/email")
    public ResponseEntity<AuthDto> loginWithEmail(@RequestBody EmailModel emailModel){

        String userId = userService.checkEmailAndPassword(emailModel);
        UserDto dto = userService.fetchUserProfile(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthDto(dto.getToken()));
    }
}
