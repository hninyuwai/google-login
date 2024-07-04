package com.sample.google_login.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sample.google_login.config.GoogleProperties;
import com.sample.google_login.constants.UserConstants;
import com.sample.google_login.dto.UserDto;
import com.sample.google_login.entity.User;
import com.sample.google_login.exception.GoogleTokenVerificationException;
import com.sample.google_login.exception.ResourceNotFoundException;
import com.sample.google_login.exception.UserAlreadyExistsException;
import com.sample.google_login.mapper.UserMapper;
import com.sample.google_login.repository.UserRepository;
import com.sample.google_login.service.UserService;
import com.sample.google_login.util.JwtUtil;
import lombok.AllArgsConstructor;
import model.EmailModel;
import model.UserModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service @AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private GoogleProperties googleProperties;

    @Override
    public String createUser(UserModel userModel) {
        User user = UserMapper.modelToUserEntity(userModel, new User());

        UUID uuid = UUID.randomUUID();
        user.setUserId(uuid.toString());

        String token = setClaims(userModel, user.getUserId());
        user.setToken(token);

        Optional<User> optionalUser = userRepository.findByEmail(userModel.getEmail());
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("Email already registered "+userModel.getEmail());
        }
        userRepository.save(user);

        return token;
    }

    @Override
    public String createGoogleUser(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleProperties.getClientId()))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                UserModel userModel = new UserModel();

                userModel.setEmail(payload.getEmail());
                userModel.setUserName((String) payload.get("name"));
                userModel.setLastName((String) payload.get("family_name"));
                userModel.setFirstName((String) payload.get("given_name"));

                User user = UserMapper.modelToUserEntity(userModel, new User());

                String token = setClaims(userModel, userId);
                user.setToken(token);

                Optional<User> optionalUser = userRepository.findByEmail(userModel.getEmail());
                if(optionalUser.isPresent()){
                    user.setId(optionalUser.get().getId());
                }
                userRepository.save(user);

                return token;

            } else {
                throw new GoogleTokenVerificationException("Invalid ID token.");
            }
        }catch (IOException | GeneralSecurityException e) {
            throw new GoogleTokenVerificationException("Failed to verify ID token",e);
        }
    }

    @Override
    public UserDto fetchUserProfile(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "UserId", userId));
        return UserMapper.entityToUserDto(user, new UserDto());
    }

    @Override
    public String checkEmailAndPassword(EmailModel emailDto) {

        User user = userRepository.findByEmailAndPassword(emailDto.getEmail(),emailDto.getPassword()).orElseThrow(
                () -> new ResourceNotFoundException(UserConstants.MESSAGE_INCORRECT));
        return user.getUserId();
    }

    @Override
    public String setClaims(UserModel userModel, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("email", userModel.getEmail());
        claims.put("name", userModel.getUserName());
        claims.put("familyName", userModel.getLastName());
        claims.put("givenName", userModel.getFirstName());

        return  JwtUtil.generateToken(claims);
    }

}
